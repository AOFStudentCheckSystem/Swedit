package guardiantech.com.cn.swedit.util

import org.junit.Test

/**
 * Created by liupeiqi on 2017/4/28.
 */
class DateTimeHelperTest {
    @Test
    fun testToday () {
        val t = System.currentTimeMillis()
        println("Current:" + t)
        println("Local:" + DateTimeHelper.firstms(true, t))
        println("UTC:" + DateTimeHelper.firstms(false, t))
        assert(DateTimeHelper.firstms(false, t) == t % 86400000)
    }
}