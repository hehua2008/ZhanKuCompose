package com.hym.zhankukotlin.util

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author hehua2008
 * @date 2022/3/28
 */

const val ONE_MINUTE_MILLIS = 60 * 1000
const val TEN_MINUTE_MILLIS = 10 * ONE_MINUTE_MILLIS
const val ONE_HOUR_MILLIS = 60 * ONE_MINUTE_MILLIS
const val ONE_DAY_MILLIS = 24 * ONE_HOUR_MILLIS

val DATE_TIME_REGEX = Regex("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")

val DATE_TIME_FORMAT = object : ThreadLocal<SimpleDateFormat>() {
    override fun initialValue() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
}

val DATE_FORMAT = object : ThreadLocal<SimpleDateFormat>() {
    override fun initialValue() = SimpleDateFormat("yyyy.MM.dd", Locale.CHINA)
}

fun Long.toDateString(): String {
    return if (this <= 0) "$this"
    else DATE_FORMAT.get()?.format(Date(this)) ?: "$this"
}

fun Long.getRelativeOrActualDateString(relativeTo: Long = System.currentTimeMillis()): String {
    return (relativeTo - this).let {
        when {
            it < TEN_MINUTE_MILLIS -> "刚刚"
            it < ONE_HOUR_MILLIS -> "${it / ONE_MINUTE_MILLIS}分钟前"
            it < ONE_DAY_MILLIS -> "${it / ONE_HOUR_MILLIS}小时前"
            else -> {
                when (val dateDiff =
                    ((relativeTo / ONE_DAY_MILLIS) - (this / ONE_DAY_MILLIS)).toInt()) {
                    0 -> "今天"
                    1 -> "昨天"
                    2 -> "前天"
                    3, 4, 5, 6, 7 -> "${dateDiff}天前"
                    else -> toDateString()
                }
            }
        }
    }
}

fun String.getDateTime(index: Int = 0): Long? {
    var count = 0
    var m: MatchResult? = DATE_TIME_REGEX.find(this)
    while (m != null) {
        if (count++ == index) {
            return DATE_TIME_FORMAT.get()?.parse(m.value, ParsePosition(0))?.time
        }
        m = m.next()
    }
    return null
}