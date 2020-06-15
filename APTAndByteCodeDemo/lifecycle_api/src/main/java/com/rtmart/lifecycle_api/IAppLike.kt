package com.rtmart.lifecycle_api

import android.content.Context

abstract class IAppLike {

    abstract val priority: Int

    abstract fun onCreate(context: Context)

    abstract fun onTerminate()

    companion object {

        val MAX_PRIORITY = 10
        val MIN_PRIORITY = 1
        val NORM_PRIORITY = 5
    }
}
