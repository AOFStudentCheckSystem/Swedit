package guardiantech.com.cn.swedit.network

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.j256.ormlite.dao.Dao
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
        val loginRequest = JsonObjectRequestPeopleCanActuallyUse(Request.Method.POST, BASE_URL + "/auth/auth", map,
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
                    Toast.makeText(APIGlobal.context, "An error occured when updating account!", Toast.LENGTH_SHORT).show()
                })
        APIGlobal.queue.add(loginRequest)
    }

}