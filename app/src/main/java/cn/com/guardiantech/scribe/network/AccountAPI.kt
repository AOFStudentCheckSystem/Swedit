package cn.com.guardiantech.scribe.network

import com.android.volley.Request
import com.android.volley.Response
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.database.item.UserItem
import cn.com.guardiantech.scribe.eventbus.Bus
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent
import cn.com.guardiantech.scribe.network.request.JsonObjectRequestPeopleCanActuallyUse

/**
 * Created by liupeiqi on 2017/4/28.
 */
object AccountAPI {

    lateinit var userDao: com.j256.ormlite.dao.Dao<UserItem, String>

    fun login(email: String, password: String, callback: (success: Boolean, error: String?) -> Unit = { _, _ -> }) {
        val map = kotlin.collections.HashMap<String, String>()
        map.put("email", email)
        map.put("password", password)
        val loginRequest = JsonObjectRequestPeopleCanActuallyUse(Request.Method.POST, Global.API.BASE_URL + "/auth/auth", map,
                Response.Listener { resp ->
                    val user = resp.getJSONObject("user")
                    if (user.optJSONObject("student") === null) {
                        callback(false, null)
                    } else {
                        userDao.create(UserItem(
                            email = user.getString("email"),
                            userLevel = user.getInt("userLevel"),
                            token = resp.getString("token")
                        ))
                        Bus.post(DBChangeEvent("users"))
                        callback(true, null)
                    }
                },
                Response.ErrorListener { e ->
                    callback(false, ErrorHandle.handle(e))
                })
        Global.API.queue.add(loginRequest)
    }

}