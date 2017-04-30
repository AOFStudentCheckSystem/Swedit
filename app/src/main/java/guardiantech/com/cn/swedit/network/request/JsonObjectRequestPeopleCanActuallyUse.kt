package guardiantech.com.cn.swedit.network.request

import org.json.JSONObject
import org.json.JSONException
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.*
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


/**
 * Created by liupeiqi on 2017/4/28.
 * Copypasta
 */
class JsonObjectRequestPeopleCanActuallyUse(requestType: Int,
                                            url: String,
                                            private val mMap: Map<String, String>,
                                            private val mListener: Response.Listener<JSONObject>,
                                            errorListener: Response.ErrorListener)
    : Request<JSONObject>(requestType, url, errorListener) {

    override fun getParams(): Map<String, String> {
        return mMap
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
        try {
            val jsonString = String(response.data, Charset.forName(HttpHeaderParser.parseCharset(response.headers)))
            return Response.success(JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            return Response.error(ParseError(e))
        } catch (je: JSONException) {
            return Response.error(ParseError(je))
        }
    }

    override fun deliverResponse(response: JSONObject) {
        mListener.onResponse(response)
    }
}