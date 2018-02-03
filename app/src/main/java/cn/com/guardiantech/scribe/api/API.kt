package cn.com.guardiantech.scribe.api

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.api.request.authentication.AuthenticationRequest
import cn.com.guardiantech.scribe.api.request.checkin.EventRequest
import cn.com.guardiantech.scribe.database.entity.ActivityEvent
import cn.com.guardiantech.scribe.database.entity.Session
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.nio.charset.Charset


/**
 * Created by dedztbh on 18-1-30.
 */
class API {
    companion object {
        //        const val BASE_URL = "https://api.aofactivities.com"
        const val BASE_URL = "http://10.0.1.20:19080"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        val apiHeaders = mutableMapOf<String, String>()

        private val queue: RequestQueue by lazy {
            val q = Volley.newRequestQueue(context)
            q.start()
            q
        }

        //Auth
        fun login(authRequest: AuthenticationRequest, callback: (success: Boolean, error: String?, sessionObject: Session?) -> Unit) {
            val requestBody = Global.mapper.writeValueAsString(authRequest)
            Log.v("login requestBody", requestBody)
            val loginRequest = object : JsonObjectRequest(
                    Request.Method.POST,
                    "$BASE_URL/auth/auth",
                    JSONObject(requestBody),
                    Response.Listener { resp ->
                        val deserializedSession = resp.toString()
                        Log.v("AccountAPILoginSuccess", deserializedSession)
                        callback(true, null, Global.mapper.readValue(deserializedSession, Session::class.java))
                    },
                    Response.ErrorListener { e ->
                        callback(false, handle(e), null)
                    }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return apiHeaders
                }
            }

            queue.add(loginRequest)
        }

        //Event
        fun fetchEventList(callback: (success: Boolean, remoteEvents: List<ActivityEvent>) -> Unit) {
            val request = object : JsonArrayRequest(
                    Request.Method.GET,
                    "$BASE_URL/checkin/event/listall",
                    null,
                    Response.Listener { eventArray ->
                        Log.v("fetchEventListSuccess", eventArray.toString())
                        callback(true, (0 until eventArray.length())
                                .map { Global.mapper.readValue(eventArray.getJSONObject(it).toString(), ActivityEvent::class.java) }
                        )
                    },
                    Response.ErrorListener {
                        Log.w("fetchEventListFail!", it)
                        Toast.makeText(context, "An error occurred when updating events!", Toast.LENGTH_SHORT).show()
                        callback(false, listOf())
                    }) {
                override fun getHeaders(): MutableMap<String, String> {
                    return apiHeaders
                }
            }
            queue.add(request)
        }

        fun editEvent(eventRequest: EventRequest, callback: (success: Boolean, error: String?, editedEvent: ActivityEvent?) -> Unit) {
            val requestBody = Global.mapper.writeValueAsString(eventRequest)
            Log.v("editEvent requestBody", requestBody)
            val editEventRequest = object : JsonObjectRequest(
                    Request.Method.POST,
                    "$BASE_URL/checkin/event/",
                    JSONObject(requestBody),
                    Response.Listener { resp ->
                        val eventJson = resp.toString()
                        Log.v("AccountAPI eventEventSuccess", eventJson)
                        callback(true, null, Global.mapper.readValue(eventJson, ActivityEvent::class.java))
                    },
                    Response.ErrorListener { e ->
                        callback(false, handle(e), null)
                    }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return apiHeaders
                }
            }

            queue.add(editEventRequest)
        }

        //Error Handle
        private fun handle(error: VolleyError): String? {
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
//                            401 -> {
//                                //token expire, unauthorized
//                                return "Credential Error!"
//                            }
//
                            403 -> {
                                //no permission
                                return "You don't have permission to do this!"
                            }
//
//                            500 -> {
//                                //401
//                                return "Credential Error!"
//                            }
//
//                            404 -> {
//                                return "Not Found!"
//                            }

                            else -> {
                                Log.i("API error", "${error.message}")
                                return "Error: ${JSONObject(error.networkResponse.data.toString(Charset.forName("UTF-8"))).getString("message")} (${error.networkResponse.statusCode})"
                            }
                        }
                    }
                }
            }
            return "No Connection!"
        }
    }
}