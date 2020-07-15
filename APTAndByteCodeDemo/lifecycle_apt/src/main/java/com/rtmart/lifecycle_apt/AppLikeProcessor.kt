package com.rtmart.lifecycle_apt

import com.google.auto.service.AutoService
import com.rtmart.lifecycle_annotation.AppLifecycle
import com.rtmart.lifecycle_annotation.LifecycleConfig
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import java.lang.reflect.Type
import java.util.HashMap

import java.util.LinkedHashSet

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

@AutoService(Processor::class)
class AppLikeProcessor : AbstractProcessor() {

    private lateinit var mElementUtils: Elements

    private val mMap = HashMap<String, AppLikeProxyClassCreator>()

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        mElementUtils = processingEnv.elementUtils
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        val set = LinkedHashSet<String>()
        set.add(AppLifecycle::class.java.canonicalName)
        return set
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val elements = roundEnv.getElementsAnnotatedWith(AppLifecycle::class.java)
        for (element in elements) {
            if (!element.kind.isClass) {
                throw RuntimeException("Annotation AppLifeCycle can only be used in class.")
            }
            val typeElement = element as TypeElement
            val mirrorList = typeElement.interfaces
            if (mirrorList.isEmpty()) {
                throw RuntimeException("${typeElement.qualifiedName} must implements interface com.hm.lifecycle.api.IAppLike")
            }
            var checkInterfaceFlag = false
            for (mirror in mirrorList) {
                if ("com.rtmart.lifecycle_api.IAppLike" == mirror.toString()) {
                    checkInterfaceFlag = true
                }
            }
            if (!checkInterfaceFlag) {
                throw RuntimeException("${typeElement.qualifiedName} must implements interface com.hm.lifecycle.api.IAppLike")
            }
            val fullClassName = typeElement.qualifiedName.toString()
            if (!mMap.containsKey(fullClassName)) {
                println("process class name : $fullClassName")
                val creator = AppLikeProxyClassCreator(mElementUtils, typeElement)
                mMap[fullClassName] = creator
            }

            // 生成Kotlin代理类
//            val proxyClassSimpleName = LifecycleConfig.PROXY_CLASS_PREFIX + typeElement.simpleName.toString() + LifecycleConfig.PROXY_CLASS_SUFFIX
//            val file = FileSpec.builder(LifecycleConfig.PROXY_CLASS_PACKAGE_NAME,proxyClassSimpleName)
//                    .addType(TypeSpec.classBuilder(proxyClassSimpleName)
//                            .build())
//                    .build()
//
//            file.writeTo(processingEnv.filer)
        }

        println("start to generate proxy class code")
        for ((className, creator) in mMap) {
            println("generate proxy class for $className")

            try {
                val jfo = processingEnv.filer.createSourceFile(creator.proxyClassFullName)
                val writer = jfo.openWriter()
                writer.write(creator.generateJavaCode())
                writer.flush()
                writer.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return true
    }
}
