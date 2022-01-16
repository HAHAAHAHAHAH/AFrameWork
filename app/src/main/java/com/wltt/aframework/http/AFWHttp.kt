package com.wltt.aframework.http

import java.io.File

interface AFWHttp {

    fun get(url: String, params: HashMap<String, String>, callBack: HttpCall)

    fun postFrom(url: String, params: HashMap<String, Any>, callBack: HttpCall)

    fun postJson(url: String, json: String, callBack: HttpCall)

    fun upload(url: String, fileType: String, filePath: String, progressCall: HttpProgressCall)

    fun upload(
        url: String,
        fileType: String,
        paramsMap: HashMap<String, Object>,
        progressCall: HttpProgressCall
    )

    fun download(url: String, filePath: String, progressCall: HttpProgressCall)

    /**
     * get 请求，拼接参数，组合成最终的url
     */
    fun assembleUrl(url: String, params: HashMap<String, String>): String {
        var result = url
        if (params.size > 0) {
            // 如果不以?结尾，拼接?
            if (!result.endsWith("?")) {
                result = "$result?"
            }
            var index = 0
            params.forEach {
                if (index == 0) {
                    result = result + it.key + "=" + it.value
                } else {
                    result = result + "&" + it.key + "=" + it.value
                }
                index++
            }
        }
        return result
    }

}