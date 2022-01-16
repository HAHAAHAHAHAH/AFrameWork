package com.wltt.aframework.http

/**
 * 上传下载回调
 */
interface HttpProgressCall {

    fun onProgress(total:Long, current:Long)

    fun onSuccess(message:String?)

    fun onFailure(errorMessage:String?)

}