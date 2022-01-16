package com.wltt.aframework.http.okhttp

import android.R.attr
import android.util.Log
import com.wltt.aframework.http.AFWHttp
import com.wltt.aframework.http.HttpCall
import com.wltt.aframework.http.HttpProgressCall
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.util.concurrent.TimeUnit
import okhttp3.MultipartBody

/**
 * okHttp 同步请求
 */
class OkHttpClient : AFWHttp {
    val TAG = "OkHttpClient"

    lateinit var okHttpClient: OkHttpClient

    var TIMEOUT = 60L

    constructor() {
        okHttpClient = OkHttpClient()
    }


    /**
     * 同步get
     */
    override fun get(url: String, params: HashMap<String, String>, callBack: HttpCall) {
        var requestUrl = assembleUrl(url, params)
        var request = okhttp3.Request.Builder().url(requestUrl).build()
        var call = okHttpClient.newCall(request)
        try {
            var response = call.execute()
            if (response.body != null) {
                callBack.onSuccess(response.body!!.string())
            } else {
                callBack.onFailure("response.body = null:response = $response")
            }

        } catch (e: IOException) {
            callBack.onFailure("IOException:message = ${e.message}")
        }
    }

    /**
     * 同步post表单
     */
    override fun postFrom(url: String, params: HashMap<String, Any>, callBack: HttpCall) {
        var body = FormBody.Builder()
        params.forEach {
            body.add(it.key, it.value.toString())
        }

        var request = okhttp3.Request.Builder().url(url).post(body.build()).build()
        var call = okHttpClient.newCall(request)
        try {
            var response = call.execute()
            if (response.body != null) {
                callBack.onSuccess(response.body!!.string())
            } else {
                callBack.onFailure("response.body = null:response = $response")
            }

        } catch (e: IOException) {
            callBack.onFailure("IOException:message = ${e.message}")
        }

    }

    /**
     * 同步post JSON
     */
    override fun postJson(url: String, json: String, callBack: HttpCall) {
        var body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        var request = okhttp3.Request.Builder().url(url).post(body).build()
        var call = okHttpClient.newCall(request)
        try {
            var response = call.execute()
            if (response.body != null) {
                callBack.onSuccess(response.body!!.string())
            } else {
                callBack.onFailure("response.body = null:response = $response")
            }

        } catch (e: IOException) {
            callBack.onFailure("IOException:message = ${e.message}")
        }
    }

    /**
     * 上传文件
     * fileType 上传文件的类型
     * 超时时间60s
     */
    override fun upload(
        url: String,
        fileType: String,
        filePath: String,
        progressCall: HttpProgressCall
    ) {

        var file:File = File(filePath)
        if (!file.exists()){
            Log.e(TAG,"上传文件失败，文件不存在！")
            progressCall.onFailure("文件不存在！")
            return
        }

        val body: RequestBody = file.asRequestBody(fileType.toMediaTypeOrNull())
        val progressBody = ProgressUpLoadBody(progressCall,body)
        val request = Request.Builder().url(url).post(progressBody).build()
        val call = okHttpClient.newBuilder().writeTimeout(TIMEOUT,TimeUnit.SECONDS).build().newCall(request)

        try {
            val response = call.execute()
            if (response.body != null) {
                progressCall.onSuccess(response.body!!.string())
            } else {
                progressCall.onFailure("response.body = null:response = $response")
            }

        } catch (e: IOException) {
            progressCall.onFailure("IOException:message = ${e.message}")
        }

    }

    /**
     * 上传文件携带参数
     *
     * paramsMap must file
     *
     */
    override fun upload(
        url: String,
        fileType:String,
        paramsMap: HashMap<String, Object>,
        progressCall: HttpProgressCall
    ) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM);

        var fileKey = ""
        var file:File? = null
        paramsMap.forEach(){
            if (it.value is File){
                fileKey = it.key
                file = it.value as File
            }else{
                builder.addFormDataPart(it.key,it.value.toString())
            }
        }

        if (file == null || !file!!.exists()){
            Log.e(TAG,"上传文件失败，文件不存在！")
            progressCall.onFailure("文件不存在！")
            return
        }

        val body: RequestBody = builder.build()
        val progressBody = ProgressUpLoadBody(progressCall,body)
        val request = Request.Builder().url(url).post(progressBody).build()
        val call = okHttpClient.newBuilder().writeTimeout(TIMEOUT,TimeUnit.SECONDS).build().newCall(request)

        try {
            val response = call.execute()
            if (response.body != null) {
                progressCall.onSuccess(response.body!!.string())
            } else {
                progressCall.onFailure("response.body = null:response = $response")
            }

        } catch (e: IOException) {
            progressCall.onFailure("IOException:message = ${e.message}")
        }

    }

    override fun download(url: String, filePath: String, progressCall: HttpProgressCall) {


    }


}