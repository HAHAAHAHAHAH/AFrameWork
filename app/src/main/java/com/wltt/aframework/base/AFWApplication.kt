package com.wltt.aframework.base

import android.app.Application
import com.wltt.aframework.screenadapter.AutoSize

/**
 * 基础AFWApplication
 * 继承即可启动AutoSize进行屏幕适配
 */
open class AFWApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AutoSize.init(this)
    }

}