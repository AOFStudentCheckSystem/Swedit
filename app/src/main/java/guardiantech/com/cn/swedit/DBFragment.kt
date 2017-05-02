package guardiantech.com.cn.swedit

import android.support.v4.app.Fragment
import android.os.Bundle
import com.google.common.eventbus.Subscribe
import com.j256.ormlite.android.apptools.OpenHelperManager
import guardiantech.com.cn.swedit.database.DBHelper
import guardiantech.com.cn.swedit.eventbus.Bus
import guardiantech.com.cn.swedit.eventbus.event.DBChangeEvent

/**
 * Created by liupeiqi on 2017/4/25.
 */
open class DBFragment(val withBus: Boolean = true): Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (withBus) Bus.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (withBus) Bus.unregister(this)
    }

    @Subscribe
    open fun onDBUpdate (dbUpdate: DBChangeEvent) {}
}