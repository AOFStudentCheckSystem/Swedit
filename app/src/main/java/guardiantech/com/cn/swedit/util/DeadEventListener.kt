package guardiantech.com.cn.swedit.util

import android.util.Log
import com.google.common.eventbus.DeadEvent
import com.google.common.eventbus.Subscribe
import guardiantech.com.cn.swedit.eventbus.Bus


/**
 * Created by liupeiqi on 2017/4/29.
 */
object DeadEventListener {
    @Subscribe
    fun deadEventReceived(e: DeadEvent) {
        Log.wtf("DeadEventListener", "not delivered:" + e.event)
    }

    init {
        Bus.register(this)
    }
}