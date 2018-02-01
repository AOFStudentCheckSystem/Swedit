package cn.com.guardiantech.scribe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.com.guardiantech.scribe.database.DBHelper
import cn.com.guardiantech.scribe.eventbus.event.EventsChangeEvent
import cn.com.guardiantech.scribe.eventbus.event.LoginEvent
import com.google.common.eventbus.Subscribe
import com.j256.ormlite.android.apptools.OpenHelperManager

/**
 * Created by liupeiqi on 2017/4/26.
 */

open class DBActivity(private val withBus: Boolean = true) : AppCompatActivity() {
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
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

    @Subscribe
    open fun onLogin(loginEvent: LoginEvent) {
    }
}