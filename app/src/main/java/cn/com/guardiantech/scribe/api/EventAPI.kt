package cn.com.guardiantech.scribe.api

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.Global
import com.android.volley.toolbox.JsonObjectRequest


/**
 * Created by liupeiqi on 2017/4/25.
 */
object EventAPI {
    fun fetchEventList(callback: (success: Boolean, remoteEvents: List<EventItem>) -> Unit) {
        val request = JsonObjectRequest(Request.Method.GET, Global.API.BASE_URL + "/event/listall", null,
                Response.Listener { response ->
                    val eventArray = response.getJSONArray("content")
//                    println("fetchEventList" + eventArray.toString())
                    callback(true, (0 until eventArray.length())
                            .map { Global.mapper.readValue(eventArray.getJSONObject(it).toString(), EventItem::class.java) }
                    )
                },
                Response.ErrorListener {
                    Toast.makeText(Global.context, "An error occured when updating events!", Toast.LENGTH_SHORT).show()
                    callback(false, listOf())
                })
        Global.API.queue.add(request)
    }
}