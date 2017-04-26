package guardiantech.com.cn.swedit.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.j256.ormlite.dao.Dao
import guardiantech.com.cn.swedit.database.persistence.EventItem
import org.json.JSONObject
import java.util.*
import com.android.volley.toolbox.Volley
import com.android.volley.RequestQueue
import guardiantech.com.cn.swedit.adapters.EventListAdapter


/**
 * Created by liupeiqi on 2017/4/25.
 */
object EventAPI {
    lateinit var context: Context
    lateinit var eventDao: Dao<EventItem, String>
    lateinit var eventListAdapter: EventListAdapter

    private var queue: RequestQueue? = null
        get() {
            if (field === null) {
                field = Volley.newRequestQueue(context)
            }
            return field
        }

    fun fetchEventList(callback: (success: Boolean) -> Unit = {}) {
        Log.wtf("WTF", "fetchEventList")
        val request = JsonObjectRequest(Request.Method.GET, "https://api.aofactivities.com/event/listall", null,
                Response.Listener<JSONObject> { response ->
                    val eventArray = response.getJSONArray("content")
                    (0 until eventArray.length())
                            .map { eventArray.getJSONObject(it) }
                            .forEach {
                                eventDao.createOrUpdate(EventItem(
                                        eventId = it.getString("eventId"),
                                        eventName = it.getString("eventName"),
                                        eventDescription = it.getString("eventDescription"),
                                        eventTime = Date(it.getLong("eventTime")),
                                        eventStatus = it.getInt("eventStatus")
                                ))
                            }
                    eventListAdapter.notifyDataSetChanged()
                    callback(true)
                },
                Response.ErrorListener {
                    Toast.makeText(context, "An Error Occured!", Toast.LENGTH_SHORT).show()
                    callback(false)
                })
        queue?.add(request)
    }
}