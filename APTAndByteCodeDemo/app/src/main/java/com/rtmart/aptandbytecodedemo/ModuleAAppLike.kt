package com.rtmart.aptandbytecodedemo

import android.content.Context
import android.util.Log

import com.rtmart.lifecycle_api.IAppLike

class ModuleAAppLike : IAppLike() {

    override val priority: Int
        get() = NORM_PRIORITY

    override fun onCreate(context: Context) {
        Log.d("AppLike", "onCreate(): this is in ModuleAAppLike.")
    }

    override fun onTerminate() {
        Log.d("AppLike", "onTerminate(): this is in ModuleAAppLike.")
    }
}
