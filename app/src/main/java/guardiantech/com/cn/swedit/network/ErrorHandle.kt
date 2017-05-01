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
                    return "Credential Error!"
                }

                403 -> {
                    //no permission
                    return "You don't have permission to do this!"
                }

                else -> {
                    return "Unknown Error: ${error.message}"
                }
            }
        }
        return "No Connection!"
    }
}