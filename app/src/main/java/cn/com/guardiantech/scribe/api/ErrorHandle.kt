package cn.com.guardiantech.scribe.api

import android.util.Log
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

/**
 * Created by liupeiqi on 2017/4/30.
 */
object ErrorHandle {
    fun handle(error: com.android.volley.VolleyError): String? {
        error.networkResponse?.let {
            when(it) {
                is com.android.volley.NoConnectionError -> {
                    return "Network Error, please retry!"
                }
                is com.android.volley.TimeoutError -> {
                    return "Network Error, please retry!"
                }
                is com.android.volley.ParseError -> {
                    return "Network Error, please retry!"
                }
                else -> {
                    when(it.statusCode) {
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