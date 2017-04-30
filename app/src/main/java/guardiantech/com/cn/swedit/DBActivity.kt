package guardiantech.com.cn.swedit

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.google.common.eventbus.Subscribe
import com.j256.ormlite.android.apptools.OpenHelperManager
import guardiantech.com.cn.swedit.database.DBHelper
import guardiantech.com.cn.swedit.eventbus.Bus
import guardiantech.com.cn.swedit.eventbus.event.DBChangeEvent
import guardiantech.com.cn.swedit.eventbus.event.LoginEvent

/**
 * Created by liupeiqi on 2017/4/26.
 */

open class DBActivity(val withDB: Boolean = true, val withBus: Boolean = true) : AppCompatActivity() {
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (withDB) dbHelper = OpenHelperManager.getHelper(applicationContext, DBHelper::class.java)
        if (withBus) Bus.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (withDB) OpenHelperManager.releaseHelper()
        if (withBus) Bus.unregister(this)
    }

    @Subscribe
    open fun onLogin (login: LoginEvent) {}
}