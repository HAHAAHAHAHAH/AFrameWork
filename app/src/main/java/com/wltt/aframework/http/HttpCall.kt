package com.wltt.aframework.http

import okio.IOException

interface HttpCall {

    fun onSuccess(result:String)

    fun onFailure(errorMessage:String?)


}