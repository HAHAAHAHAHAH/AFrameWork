package com.wltt.aframework.http

import com.google.gson.Gson
import kotlinx.coroutines.*
import com.google.gson.reflect.TypeToken
import com.wltt.aframework.http.okhttp.OkHttpClient
import java.lang.reflect.Type

/**
 * 网络请求类
 */
object HttpRequest : Request {

    private var afwHttp = OkHttpClient()
    private var mGson = Gson()

    /**
     * 异步get
     */
    @DelicateCoroutinesApi
    override fun <T> asynGet(
        url: String,
        params: HashMap<String, String>,
        requestCall: RequestCall<T>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            async {
                afwHttp.get(url, params, object : HttpCall {
                    override fun onSuccess(result: String) {
                        GlobalScope.launch(Dispatchers.Main) {
                            // 进行解析
                            val jsonType: Type =
                                object : TypeToken<Response<T>>() {}.type
                            val response: Response<T> = mGson.fromJson(result, jsonType)
                            requestCall.onSuccess(response.body)
                        }
                    }

                    override fun onFailure(errorMessage: String?) {
                        GlobalScope.launch(Dispatchers.Main) {
                            requestCall.onFailure(errorMessage)
                        }
                    }

                })
            }
        }
    }

    /**
     * 异步 postFrom
     */
    @DelicateCoroutinesApi
    override fun <T> asynPostFrom(
        url: String,
        params: HashMap<String, Any>,
        requestCall: RequestCall<T>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            async {
                afwHttp.postFrom(url, params, object : HttpCall {
                    override fun onSuccess(result: String) {
                        GlobalScope.launch(Dispatchers.Main) {
                            // 进行解析
                            val jsonType: Type =
                                object : TypeToken<Response<T>>() {}.type
                            val response: Response<T> = mGson.fromJson(result, jsonType)
                            requestCall.onSuccess(response.body)
                        }
                    }

                    override fun onFailure(errorMessage: String?) {
                        GlobalScope.launch(Dispatchers.Main) {
                            requestCall.onFailure(errorMessage)
                        }
                    }

                })
            }
        }
    }

    /**
     * 异步 postJson
     */
    @DelicateCoroutinesApi
    override fun <T> asynPostJson(
        url: String,
        json: String,
        requestCall: RequestCall<T>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            async {
                afwHttp.postJson(url, json, object : HttpCall {
                    override fun onSuccess(result: String) {
                        GlobalScope.launch(Dispatchers.Main) {
                            // 进行解析
                            val jsonType: Type =
                                object : TypeToken<Response<T>>() {}.type
                            val response: Response<T> = mGson.fromJson(result, jsonType)
                            requestCall.onSuccess(response.body)
                        }
                    }

                    override fun onFailure(errorMessage: String?) {
                        GlobalScope.launch(Dispatchers.Main) {
                            requestCall.onFailure(errorMessage)
                        }
                    }

                })
            }
        }
    }

    /**
     * 上传文件
     */
    override fun <T> upload(
        url: String,
        fileType: String,
        filePath: String,
        requestCall: RequestCall<T>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            async {
                afwHttp.upload(url, fileType, filePath, object : HttpProgressCall {

                    override fun onProgress(total: Long, current: Long) {
                        requestCall.onProgress(total, current)
                    }

                    override fun onSuccess(result: String?) {
                        GlobalScope.launch(Dispatchers.Main) {
                            // 进行解析
                            val jsonType: Type =
                                object : TypeToken<Response<T>>() {}.type
                            val response: Response<T> = mGson.fromJson(result, jsonType)
                            requestCall.onSuccess(response.body)
                        }
                    }

                    override fun onFailure(errorMessage: String?) {
                        GlobalScope.launch(Dispatchers.Main) {
                            requestCall.onFailure(errorMessage)
                        }
                    }

                })
            }
        }
    }


}