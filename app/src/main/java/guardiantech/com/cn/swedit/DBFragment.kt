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
open class DBFragment(val withDB: Boolean = true, val withBus: Boolean = true): Fragment() {
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (withDB) dbHelper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        if (withBus) Bus.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (withDB) OpenHelperManager.releaseHelper()
        if (withBus) Bus.unregister(this)
    }

    @Subscribe
    open fun onDBUpdate (dbUpdate: DBChangeEvent) {}
}