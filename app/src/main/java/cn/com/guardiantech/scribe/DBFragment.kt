package cn.com.guardiantech.scribe

import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent

/**
 * Created by liupeiqi on 2017/4/25.
 */
open class DBFragment(val withBus: Boolean = true): android.support.v4.app.Fragment() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        if (withBus) Global.bus.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (withBus) Global.bus.unregister(this)
    }

    @com.google.common.eventbus.Subscribe
    open fun onDBUpdate (dbUpdate: DBChangeEvent) {}
}