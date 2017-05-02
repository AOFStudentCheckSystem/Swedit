package guardiantech.com.cn.swedit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.common.eventbus.Subscribe
import guardiantech.com.cn.swedit.eventbus.Bus
import guardiantech.com.cn.swedit.eventbus.event.LoginEvent

/**
 * Created by liupeiqi on 2017/4/26.
 */

open class DBActivity(val withBus: Boolean = true) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (withBus) Bus.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            if (withBus) Bus.unregister(this)
        }
    }

    @Subscribe
    open fun onLogin(login: LoginEvent) {
    }
}