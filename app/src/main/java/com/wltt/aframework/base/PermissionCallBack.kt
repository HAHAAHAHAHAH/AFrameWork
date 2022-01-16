package com.wltt.aframework.base

/**
 * 权限申请callback
 */
interface PermissionCallBack {
    fun onPermission(granted:Boolean,permanentRejectionList:ArrayList<String>)
}