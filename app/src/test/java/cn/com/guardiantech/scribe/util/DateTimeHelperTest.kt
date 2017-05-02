package cn.com.guardiantech.scribe.util

import cn.com.guardiantech.scribe.util.DateTimeHelper
import org.junit.Test

/**
 * Created by liupeiqi on 2017/4/28.
 */
class DateTimeHelperTest {
    @org.junit.Test
    fun testToday () {
        val t = System.currentTimeMillis()
        println("Current:" + t)
        println("Local:" + cn.com.guardiantech.scribe.util.DateTimeHelper.firstms(true, t))
        println("UTC:" + cn.com.guardiantech.scribe.util.DateTimeHelper.firstms(false, t))
        assert(cn.com.guardiantech.scribe.util.DateTimeHelper.firstms(false, t) == t % 86400000)
    }
}