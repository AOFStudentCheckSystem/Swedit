package guardiantech.com.cn.swedit.util

import java.util.*

/**
 * Created by liupeiqi on 2017/4/28.
 */
object DateTimeHelper {

    fun firstms(local: Boolean = true, time: Long = System.currentTimeMillis()): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        calendar.timeZone = if (local) TimeZone.getDefault() else TimeZone.getTimeZone("UTC")
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun lastms (local: Boolean = true, time: Long = System.currentTimeMillis()): Long {
        return firstms(local, time) + 86400000
    }
}