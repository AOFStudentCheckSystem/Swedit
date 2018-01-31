package cn.com.guardiantech.scribe.api

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.database.item.UserItem
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


/**
 * Created by dedztbh on 18-1-30.
 */
class API {
    companion object {
        const val BASE_URL = "https://api.aofactivities.com"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        private val queue: RequestQueue by lazy {
            val q = Volley.newRequestQueue(context)
            q.start()
            q
        }

        fun login(email: String, password: String, callback: (success: Boolean, error: String?, userObject: UserItem?) -> Unit) {
            val map = JSONObject()
            map.put("email", email)
            map.put("password", password)
            val loginRequest = JsonObjectRequest(Request.Method.POST, "$BASE_URL/auth/auth", map,
                    Response.Listener { resp ->
                        Log.w("AccountAPILoginSuccess", resp.toString())
                        val user = resp.getJSONObject("user")
//                    if (user.optJSONObject("student") === null) {
//                        callback(false, null)
//                    }
                        callback(true, null, Global.mapper.readValue(user.toString(), UserItem::class.java))
                    },
                    Response.ErrorListener { e ->
                        callback(false, handle(e), null)
                    })
            queue.add(loginRequest)
        }

        fun fetchEventList(callback: (success: Boolean, remoteEvents: List<EventItem>) -> Unit) {
            val request = JsonObjectRequest(Request.Method.GET, "$BASE_URL/event/listall", null,
                    Response.Listener { response ->
                        val eventArray = response.getJSONArray("content")
//                    println("fetchEventList" + eventArray.toString())
                        callback(true, (0 until eventArray.length())
                                .map { Global.mapper.readValue(eventArray.getJSONObject(it).toString(), EventItem::class.java) }
                        )
                    },
                    Response.ErrorListener {
                        Toast.makeText(context, "An error occured when updating events!", Toast.LENGTH_SHORT).show()
                        callback(false, listOf())
                    })
            queue.add(request)
        }

        fun handle(error: VolleyError): String? {
            error.networkResponse?.let {
                when (it) {
                    is NoConnectionError -> {
                        return "Network Error, please retry!"
                    }
                    is TimeoutError -> {
                        return "Network Error, please retry!"
                    }
                    is ParseError -> {
                        return "Network Error, please retry!"
                    }
                    else -> {
                        when (it.statusCode) {
                            401 -> {
                                //token expire, unauthorized
                                return "Credential Error!"
                            }

                            403 -> {
                                //no permission
                                return "You don't have permission to do this!"
                            }

                            500 -> {
                                //401
                                return "Credential Error!"
                            }

                            else -> {
                                return "Unknown Error: ${error.message}"
                            }
                        }
                    }
                }
            }
            return "No Connection!"
        }
    }
}