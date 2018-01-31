package cn.com.guardiantech.scribe

import android.os.Bundle
import android.support.v4.app.Fragment
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent

/**
 * Created by liupeiqi on 2017/4/25.
 */
open class DBFragment : Fragment() {

    var withBus = true
        set(newVal) {
            field = newVal
            if (newVal) {
                Global.bus.register(this)
            } else {
                Global.bus.unregister(this)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (withBus) Global.bus.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (withBus) Global.bus.unregister(this)
    }

    @com.google.common.eventbus.Subscribe
    open fun onDBUpdate(dbUpdate: DBChangeEvent) {
    }
}