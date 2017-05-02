package guardiantech.com.cn.swedit.network

import com.android.volley.Request
import com.android.volley.Response
import com.j256.ormlite.dao.Dao
import guardiantech.com.cn.swedit.Global
import guardiantech.com.cn.swedit.database.item.UserItem
import guardiantech.com.cn.swedit.eventbus.Bus
import guardiantech.com.cn.swedit.eventbus.event.DBChangeEvent
import guardiantech.com.cn.swedit.network.request.JsonObjectRequestPeopleCanActuallyUse
import kotlin.collections.HashMap

/**
 * Created by liupeiqi on 2017/4/28.
 */
object AccountAPI {

    lateinit var userDao: Dao<UserItem, String>

    fun login(email: String, password: String, callback: (success: Boolean, error: String?) -> Unit = { _, _ -> }) {
        val map = HashMap<String, String>()
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