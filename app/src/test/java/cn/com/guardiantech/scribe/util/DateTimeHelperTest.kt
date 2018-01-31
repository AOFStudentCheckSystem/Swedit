package cn.com.guardiantech.scribe.util

import junit.framework.Assert.assertTrue

/**
 * Created by liupeiqi on 2017/4/28.
 */
class DateTimeHelperTest {
    @org.junit.Test
    fun testToday() {
        val t = System.currentTimeMillis()
        println("Current:" + t)
        val localFirstMs = cn.com.guardiantech.scribe.util.DateTimeHelper.firstms(t)
        println("LocalFirstMs:" + cn.com.guardiantech.scribe.util.DateTimeHelper.firstms(t))
//        assertTrue((localFirstMs % 86400000 == 68400000L) || (localFirstMs % 86400000 == 18000000L))
        assertTrue(localFirstMs <= t)
    }
}