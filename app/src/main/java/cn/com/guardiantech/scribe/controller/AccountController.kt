package cn.com.guardiantech.scribe.controller

import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.api.API
import cn.com.guardiantech.scribe.database.item.UserItem
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent
import cn.com.guardiantech.scribe.eventbus.event.LoginEvent

/**
 * Created by liupeiqi on 2017/9/26.
 */
class AccountController {
    companion object {
        lateinit var userDao: com.j256.ormlite.dao.Dao<UserItem, String>

        fun login(email: String, passowrd: String, callback: () -> Unit) {
            API.login(email, passowrd) { success, error, userObject ->
                if (success) {
                    userDao.createOrUpdate(userObject)
                }
                Global.bus.post(DBChangeEvent("users"))
                Global.bus.post(LoginEvent(success, error ?: "Unknown"))
                callback()
            }
        }
    }
}