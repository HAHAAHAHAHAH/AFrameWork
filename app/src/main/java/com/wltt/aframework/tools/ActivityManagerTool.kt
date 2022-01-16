package com.wltt.aframework.tools

import com.wltt.aframework.base.AFWActivity

/**
 * Activity控制工具
 * 在activity onCreate和destroy的时候，加入或清除列表
 */
object ActivityManagerTool {

    var activityList: ArrayList<AFWActivity> = ArrayList()

    fun createActivity(activity: AFWActivity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity)
        }
    }

    fun destroyActivity(activity: AFWActivity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity)
        }
    }

    fun clearAllActivity() {
        for (activity: AFWActivity in activityList) {
            activity.finish()
        }
        activityList.clear()
    }

}