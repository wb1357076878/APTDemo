package com.rtmart.lifecycle_api

import android.content.Context

import java.util.ArrayList
import java.util.Collections
import java.util.Comparator

object AppLifecycleManager {

    private val APP_LIKE_LIST = ArrayList<IAppLike>()

    /**
     * 注册IAppLike类
     * @param appLike
     */
    fun registerAppLike(appLike: IAppLike) {
        APP_LIKE_LIST.add(appLike)
    }

    /**
     * 初始化，需要在Application.onCreate()里调用
     * @param context
     */
    fun init(context: Context) {
        Collections.sort(APP_LIKE_LIST, AppLikeComparator())
        for (appLike in APP_LIKE_LIST) {
            appLike.onCreate(context)
        }
    }

    fun terminate() {
        for (appLike in APP_LIKE_LIST) {
            appLike.onTerminate()
        }
    }


    /**
     * 优先级比较器，优先级大的排在前面
     */
    internal class AppLikeComparator : Comparator<IAppLike> {
        override fun compare(o1: IAppLike, o2: IAppLike): Int {

            val p1 = o1.priority
            val p2 = o2.priority
            return p2 - p1
        }
    }
}
