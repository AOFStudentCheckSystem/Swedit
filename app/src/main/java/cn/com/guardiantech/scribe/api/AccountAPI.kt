package cn.com.guardiantech.scribe.api

import com.android.volley.Request
import com.android.volley.Response
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.database.item.UserItem
import cn.com.guardiantech.scribe.api.request.JsonObjectRequestPeopleCanActuallyUse

/**
 * Created by liupeiqi on 2017/4/28.
 */
object AccountAPI {

    fun login(email: String, password: String, callback: (success: Boolean, error: String?, userObject: UserItem?) -> Unit) {
        val map = kotlin.collections.HashMap<String, String>()
        map.put("email", email)
        map.put("password", password)
        val loginRequest = JsonObjectRequestPeopleCanActuallyUse(Request.Method.POST, Global.API.BASE_URL + "/auth/auth", map,
                Response.Listener { resp ->
                    val user = resp.getJSONObject("user")
//                    if (user.optJSONObject("student") === null) {
//                        callback(false, null)
//                    }
                    callback(true, null, Global.mapper.readValue(user.toString(), UserItem::class.java))
                },
                Response.ErrorListener { e ->
                    callback(false, ErrorHandle.handle(e), null)
                })
        Global.API.queue.add(loginRequest)
    }

}