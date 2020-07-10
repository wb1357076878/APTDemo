/**
 * Copyright (C), 2015-2020, 飞牛集达有限公司
 * FileName: ModuleBAppLike
 * Author: WangBo
 * Date: 2020-06-15 11:34
 * Description:
 * History:
 */
package com.rtmart.aptandbytecodedemo

import android.content.Context
import android.util.Log
import com.rtmart.lifecycle_annotation.AppLifecycle
import com.rtmart.lifecycle_api.IAppLike

@AppLifecycle
class ModuleBAppLike : IAppLike {

    override val priority: Int
        get() = IAppLike.MIN_PRIORITY

    override fun onCreate(context: Context) {
        Log.d("AppLike", "onCreate(): this is in ModuleAAppLike.")
    }

    override fun onTerminate() {
        Log.d("AppLike", "onTerminate(): this is in ModuleAAppLike.")
    }
}