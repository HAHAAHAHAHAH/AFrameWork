package com.wltt.aframework.tools

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间转换工具
 */
object DateTool {
    const val FORMAT = "yyyy-MM-dd HH:mm:ss"

    /**
     * 时间戳转换为需要的格式
     *
     * @param time
     * @param pattern 为空则默认yyyy-MM-dd HH:mm:ss
     * @return
     */
    fun getDateToString(time: Long, pattern: String?): String {
        var pattern = pattern
        if (pattern.isNullOrEmpty()) {
            pattern = FORMAT
        }
        val format = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        return format.format(time)
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString
     * @param pattern
     * @return
     */
    fun getStringToDate(dateString: String?, pattern: String?): Long {
        val dateFormat = SimpleDateFormat(pattern)
        var date = Date()
        try {
            date = dateFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date.time
    }
}