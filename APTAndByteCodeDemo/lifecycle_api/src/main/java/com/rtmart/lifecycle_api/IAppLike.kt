package com.rtmart.lifecycle_api

import android.content.Context

interface IAppLike {

    val priority: Int

    fun onCreate(context: Context)

    fun onTerminate()

    companion object {

        val MAX_PRIORITY = 10
        val MIN_PRIORITY = 1
        val NORM_PRIORITY = 5
    }
}
