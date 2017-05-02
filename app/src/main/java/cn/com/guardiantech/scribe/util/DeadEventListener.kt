package cn.com.guardiantech.scribe.util

import cn.com.guardiantech.scribe.eventbus.Bus


/**
 * Created by liupeiqi on 2017/4/29.
 */
object DeadEventListener {
    @com.google.common.eventbus.Subscribe
    fun deadEventReceived(e: com.google.common.eventbus.DeadEvent) {
        android.util.Log.wtf("DeadEventListener", "not delivered:" + e.event)
    }

    init {
        Bus.register(this)
    }
}