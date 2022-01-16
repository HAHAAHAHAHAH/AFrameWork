package com.wltt.aframework.tools

import android.util.Log
import com.wltt.aframework.BuildConfig

/**
 * Log 输出工具类
 * debug版本输出日志
 */
object LogTool {
    fun d(tag: String, str: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, str)
        }
    }

    fun i(tag: String, str: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, str)
        }
    }


    fun e(tag: String, str: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, str)
        }
    }

    fun w(tag: String, str: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, str)
        }
    }

    fun v(tag: String, str: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, str)
        }
    }


}