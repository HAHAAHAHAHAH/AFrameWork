package com.wltt.aframework.http

interface RequestCall<T> {

    fun onSuccess(result: T?)

    fun onFailure(errorMessage: String?)

    /**
     * 上传下载回调进度
     */
    fun onProgress(total: Long, current: Long) {

    }
}