package cn.com.guardiantech.scribe

import cn.com.guardiantech.scribe.eventbus.event.LoginEvent

/**
 * Created by liupeiqi on 2017/4/26.
 */

open class DBActivity(val withBus: Boolean = true) : android.support.v7.app.AppCompatActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        if (withBus) Global.bus.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            if (withBus) Global.bus.unregister(this)
        }
    }

    @com.google.common.eventbus.Subscribe
    open fun onLogin(login: LoginEvent) {
    }
}