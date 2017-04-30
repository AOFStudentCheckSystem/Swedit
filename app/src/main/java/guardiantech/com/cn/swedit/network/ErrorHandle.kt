package guardiantech.com.cn.swedit.network

import com.android.volley.*

/**
 * Created by liupeiqi on 2017/4/30.
 */
object ErrorHandle {
    fun handle(error: VolleyError): String? {
        error.networkResponse?.let {
            if (it is NoConnectionError || it is TimeoutError || it is ParseError) return "Network Error, please retry!"
            when(it.statusCode) {
                401 -> {
                    //token expire, unauthorized
                }

                403 -> {
                    //no permission
                }

                else -> {
                    return "Unknown Error"
                }
            }
        }
        return "No Connection!"
    }
}