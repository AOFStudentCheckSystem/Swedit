package guardiantech.com.cn.swedit

import android.app.Application
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.MsgConstant
import com.umeng.message.PushAgent

/**
 * Created by liupeiqi on 2017/5/2.
 */
class SweditApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val mPushAgent = PushAgent.getInstance(this)
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
            }

            override fun onFailure(s: String, s1: String) {
            }
        })

        PushAgent.getInstance(applicationContext).onAppStart()

        mPushAgent.notificationPlaySound = MsgConstant.NOTIFICATION_PLAY_SERVER
        mPushAgent.notificationPlayLights = MsgConstant.NOTIFICATION_PLAY_SERVER
        mPushAgent.notificationPlayVibrate = MsgConstant.NOTIFICATION_PLAY_SERVER
    }
}