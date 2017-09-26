package cn.com.guardiantech.scribe.api.request

import org.json.JSONObject


/**
 * Created by liupeiqi on 2017/4/28.
 * Copypasta
 */
class JsonObjectRequestPeopleCanActuallyUse(requestType: Int,
                                            url: String,
                                            private val mMap: Map<String, String>,
                                            private val mListener: com.android.volley.Response.Listener<JSONObject>,
                                            errorListener: com.android.volley.Response.ErrorListener)
    : com.android.volley.Request<JSONObject>(requestType, url, errorListener) {

    override fun getParams(): Map<String, String> {
        return mMap
    }

    override fun parseNetworkResponse(response: com.android.volley.NetworkResponse): com.android.volley.Response<JSONObject> {
        try {
            val jsonString = String(response.data, java.nio.charset.Charset.forName(com.android.volley.toolbox.HttpHeaderParser.parseCharset(response.headers)))
            return com.android.volley.Response.success(org.json.JSONObject(jsonString), com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: java.io.UnsupportedEncodingException) {
            return com.android.volley.Response.error(com.android.volley.ParseError(e))
        } catch (je: org.json.JSONException) {
            return com.android.volley.Response.error(com.android.volley.ParseError(je))
        }
    }

    override fun deliverResponse(response: org.json.JSONObject) {
        mListener.onResponse(response)
    }
}