package com.rtmart.lifecycle_apt

import com.rtmart.lifecycle_annotation.LifecycleConfig

import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

class AppLikeProxyClassCreator(private val mElementUtils: Elements, private val mTypeElement: TypeElement) {
    private val mProxyClassSimpleName: String

    /**
     * 获取要生成的代理类的完整类名
     *
     * @return
     */
    val proxyClassFullName: String
        get() = LifecycleConfig.PROXY_CLASS_PACKAGE_NAME + "." + mProxyClassSimpleName

    init {
        mProxyClassSimpleName = LifecycleConfig.PROXY_CLASS_PREFIX +
                mTypeElement.simpleName.toString() +
                LifecycleConfig.PROXY_CLASS_SUFFIX
    }

    /**
     * 生成java代码
     */
    fun generateJavaCode(): String {
        val sb = StringBuilder()
        //设置包名
        sb.append("package ").append(LifecycleConfig.PROXY_CLASS_PACKAGE_NAME).append(";\n\n")

        //设置import部分
        sb.append("import android.content.Context;\n")
        sb.append("import com.rtmart.lifecycle_api.IAppLike;\n")
        sb.append("import ").append(mTypeElement.qualifiedName).append(";\n\n")

        sb.append("public class ").append(mProxyClassSimpleName)
                .append(" implements ").append("IAppLike ").append(" {\n\n")

        //设置变量
        sb.append("  private ").append(mTypeElement.simpleName.toString()).append(" mAppLike;\n\n")

        //构造函数
        sb.append("  public ").append(mProxyClassSimpleName).append("() {\n")
        sb.append("  mAppLike = new ").append(mTypeElement.simpleName.toString()).append("();\n")
        sb.append("  }\n\n")

        //onCreate()方法
        sb.append("  public void onCreate(Context context) {\n")
        sb.append("    mAppLike.onCreate(context);\n")
        sb.append("  }\n\n")

        //getPriority()方法
        sb.append("  public int getPriority() {\n")
        sb.append("    return mAppLike.getPriority();\n")
        sb.append("  }\n\n")

        //onTerminate方法
        sb.append("  public void onTerminate() {\n")
        sb.append("    mAppLike.onTerminate();\n")
        sb.append("  }\n\n")


        sb.append("\n}")
        return sb.toString()
    }

}
