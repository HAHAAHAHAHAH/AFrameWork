package com.wltt.aframework.http

interface Request {

    fun <T> asynGet(url: String, params: HashMap<String, String>, requestCall: RequestCall<T>)

    fun <T> asynPostFrom(url: String, params: HashMap<String, Any>, requestCall: RequestCall<T>)

    fun <T> asynPostJson(url: String, json: String, requestCall: RequestCall<T>)

    fun<T>  upload(url: String, fileType: String, filePath: String,requestCall: RequestCall<T>)

}