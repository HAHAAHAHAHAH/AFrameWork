package com.wltt.aframework.screenadapter

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.runBlocking

/**
 * @author wulitaotao
 * 参考AndroidAutoSize，实现的今日头条屏幕适配方案
 */
object AutoSize {
    val TAG = "AutoSize"

    val KEY_DESIGN_WIDTH_IN_DP = "design_width_in_dp"
    val KEY_DESIGN_HEIGHT_IN_DP = "design_height_in_dp"

    var mDesignWidthInDp = 0
    var mDesignHeightInDp = 0

    var mAppDensity: Float = 0f
    var mAppScaledDensity: Float = 0f
    var mIsWidth = true


    fun init(application: Application) {

        runBlocking {
            getMetaData(application)
        }

        if (mDesignWidthInDp == 0 || mDesignHeightInDp == 0) {
            Log.e(TAG, "没有在AndrodManifest配置设计图信息")
            return
        }
        Log.d(
            TAG,
            "初始化成功! \n mDesignWidthInDp = $mDesignWidthInDp \n mDesignHeightInDp = $mDesignHeightInDp"
        )
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                autoConvertDensity(activity, application)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(p0: Activity) {

            }

            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStopped(p0: Activity) {

            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

            }

            override fun onActivityDestroyed(p0: Activity) {

            }
        })
    }

    /**
     * 获取使用者在 AndroidManifest 中填写的 Meta 信息
     */
    fun getMetaData(context: Context) {

        val packageManager = context.packageManager
        val applicationInfo: ApplicationInfo
        try {
            applicationInfo = packageManager.getApplicationInfo(
                context
                    .packageName, PackageManager.GET_META_DATA
            )
            if (applicationInfo != null && applicationInfo.metaData != null) {
                if (applicationInfo.metaData.containsKey(KEY_DESIGN_WIDTH_IN_DP)) {
                    mDesignWidthInDp =
                        applicationInfo.metaData[KEY_DESIGN_WIDTH_IN_DP] as Int
                }
                if (applicationInfo.metaData.containsKey(KEY_DESIGN_HEIGHT_IN_DP)) {
                    mDesignHeightInDp =
                        applicationInfo.metaData[KEY_DESIGN_HEIGHT_IN_DP] as Int
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    /**
     * 使用AndroidManifest中的设计图信息，设置density
     */
    fun autoConvertDensity(activity: Activity, application: Application) {
        var appDisplayMetrics = application.resources.displayMetrics
        if (mAppDensity == 0f) {
            mAppDensity = appDisplayMetrics.density
            mAppScaledDensity = appDisplayMetrics.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        mAppScaledDensity = application.resources.displayMetrics.scaledDensity;
                    }
                }

                override fun onLowMemory() {
                }

            })
            var targetDensity: Float = if (mIsWidth) {
                (appDisplayMetrics.widthPixels / mDesignWidthInDp).toFloat()
            } else {
                (appDisplayMetrics.heightPixels / mDesignHeightInDp).toFloat()
            }
            var targetScaledDensity: Float = targetDensity * (mAppScaledDensity / mAppDensity)
            var targetDensityDpi: Int = (160 * targetDensity).toInt()

            appDisplayMetrics.density = targetDensity
            appDisplayMetrics.scaledDensity = targetScaledDensity;
            appDisplayMetrics.densityDpi = targetDensityDpi;

            var activityDisplayMetrics = activity.resources.displayMetrics
            activityDisplayMetrics.density = targetDensity
            activityDisplayMetrics.scaledDensity = targetScaledDensity
            activityDisplayMetrics.densityDpi = targetDensityDpi

        }
    }


    /**
     * 使用自定义的设计图，设置density
     * isWidth 是否已宽度适配 是 = true 否 = false
     * cusDesignInDp 自定义的设计图参数，以dp为单位，isWidth = true,使用设计图中的屏幕宽度，isWidth = false,使用设计图中的屏幕高度
     * isWidth 是否已宽度适配
     *
     */
    fun autoConvertDensity(
        activity: Activity,
        application: Application,
        cusDesignInDp: Int,
        isWidth: Boolean
    ) {
        var appDisplayMetrics = application.resources.displayMetrics
        if (mAppDensity == 0f) {
            mAppDensity = appDisplayMetrics.density
            mAppScaledDensity = appDisplayMetrics.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        mAppScaledDensity = application.resources.displayMetrics.scaledDensity;
                    }
                }

                override fun onLowMemory() {
                }

            })

            var targetDensity: Float = if (isWidth) {
                (appDisplayMetrics.widthPixels / cusDesignInDp).toFloat()
            } else {
                (appDisplayMetrics.heightPixels / cusDesignInDp).toFloat()
            }

            var targetScaledDensity: Float = targetDensity * (mAppScaledDensity / mAppDensity)
            var targetDensityDpi: Int = (160 * targetDensity).toInt()

            appDisplayMetrics.density = targetDensity
            appDisplayMetrics.scaledDensity = targetScaledDensity;
            appDisplayMetrics.densityDpi = targetDensityDpi;

            var activityDisplayMetrics = activity.resources.displayMetrics
            activityDisplayMetrics.density = targetDensity
            activityDisplayMetrics.scaledDensity = targetScaledDensity
            activityDisplayMetrics.densityDpi = targetDensityDpi

        }
    }


}