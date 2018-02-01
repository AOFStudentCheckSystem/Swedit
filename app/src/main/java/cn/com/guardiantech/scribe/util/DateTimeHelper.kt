package cn.com.guardiantech.scribe.util

/**
 * Created by liupeiqi on 2017/4/28.
 */
object DateTimeHelper {

    fun firstms(time: Long = System.currentTimeMillis()): Long {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = time
        calendar.timeZone = java.util.TimeZone.getDefault()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun lastms(time: Long = System.currentTimeMillis()): Long {
        return firstms(time) + 86399999
    }
}