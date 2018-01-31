package cn.com.guardiantech.scribe

import cn.com.guardiantech.scribe.database.DBHelper
import cn.com.guardiantech.scribe.eventbus.event.LoginEvent
import com.j256.ormlite.android.apptools.OpenHelperManager

/**
 * Created by liupeiqi on 2017/4/26.
 */

open class DBActivity(val withBus: Boolean = true) : android.support.v7.app.AppCompatActivity() {
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        if (withBus) Global.bus.register(this)
        dbHelper = OpenHelperManager.getHelper(applicationContext, DBHelper::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            if (withBus) Global.bus.unregister(this)
            OpenHelperManager.releaseHelper()
        }
    }

    @com.google.common.eventbus.Subscribe
    open fun onLogin(login: LoginEvent) {
    }
}