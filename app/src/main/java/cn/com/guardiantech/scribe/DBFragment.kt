package cn.com.guardiantech.scribe

import android.app.Fragment
import android.os.Bundle
import cn.com.guardiantech.scribe.eventbus.event.EventsChangeEvent
import cn.com.guardiantech.scribe.util.Tagable
import com.google.common.eventbus.Subscribe

/**
 * Created by liupeiqi on 2017/4/25.
 */
abstract class DBFragment : Fragment(), Tagable {

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

    @Subscribe
    open fun onEventsChange(eventsChangeEvent: EventsChangeEvent) {
    }
}