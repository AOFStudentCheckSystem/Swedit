package cn.com.guardiantech.scribe.util

import java.util.*

/**
 * Created by liupeiqi on 2017/4/28.
 */
object DateTimeHelper {

    fun firstms(local: Boolean = true, time: Long = System.currentTimeMillis()): Long {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = time
        calendar.timeZone = if (local) java.util.TimeZone.getDefault() else java.util.TimeZone.getTimeZone("UTC")
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun lastms (local: Boolean = true, time: Long = System.currentTimeMillis()): Long {
        return firstms(local, time) + 86400000
    }
}