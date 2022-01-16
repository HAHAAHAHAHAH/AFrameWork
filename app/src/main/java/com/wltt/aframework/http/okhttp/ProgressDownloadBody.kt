package com.wltt.aframework.http.okhttp

import com.wltt.aframework.http.HttpProgressCall
import okhttp3.MediaType

import okhttp3.ResponseBody
import okio.*


class ProgressDownloadBody : ResponseBody {

    //实际的待包装响应体
    private lateinit var responseBody: ResponseBody

    //进度回调接口
    private lateinit var progressCall: HttpProgressCall

    //包装完成的BufferedSource
    private var bufferedSource: BufferedSource? = null

    constructor(responseBody: ResponseBody, progressCall: HttpProgressCall) : super() {
        this.responseBody = responseBody
        this.progressCall = progressCall
    }


    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            //包装
            bufferedSource = source(responseBody.source()).buffer()
        }
        return bufferedSource!!
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            //当前读取字节数
            var totalBytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0

                progressCall.onProgress(responseBody.contentLength(), totalBytesRead)

                return bytesRead
            }
        }
    }
}