package com.wltt.aframework.http.okhttp

import com.wltt.aframework.http.HttpProgressCall
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*


/**
 * okhttp 上传回调进度的requestbody
 */
class ProgressUpLoadBody : RequestBody {

    lateinit var progressCall:HttpProgressCall
    lateinit var requestBody: RequestBody

    private var bufferedSink: BufferedSink? = null
    constructor(progressCall: HttpProgressCall, requestBody: RequestBody) : super() {
        this.progressCall = progressCall
        this.requestBody = requestBody
    }


    override fun contentType(): MediaType? {
        return requestBody.contentType();
    }

    override fun writeTo(sink: BufferedSink) {
        if (bufferedSink == null) {
            bufferedSink = sink(sink).buffer()
        }
        requestBody.writeTo(bufferedSink!!)
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink!!.flush();
    }


    private fun sink(sink:Sink): Sink {
        return object : ForwardingSink(sink) {
            var bytesWritten = 0L
            var contentLength = 0L


            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)

                if (contentLength == 0L) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                if(progressCall != null){
                    progressCall.onProgress(contentLength,bytesWritten)
                }

            }
        }
    }

}