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
class EventAPI(private val context: Context, private val eventDao: Dao<EventItem, String>, private val mAdapter: EventListAdapter) {

    private var queue = Volley.newRequestQueue(context)

    fun fetchEventList() {
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
                                        eventStatus = it.getLong("eventStatus")
                                ))
                            }
                    mAdapter.notifyDataSetChanged()
                },
                Response.ErrorListener {
                    Toast.makeText(context, "An Error Occured!", Toast.LENGTH_SHORT).show()
                })
        queue.add(request)
    }
}