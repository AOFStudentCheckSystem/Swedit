package guardiantech.com.cn.swedit.network

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.j256.ormlite.dao.Dao
import guardiantech.com.cn.swedit.database.item.EventItem
import java.util.*
import com.j256.ormlite.table.TableUtils
import guardiantech.com.cn.swedit.Global
import guardiantech.com.cn.swedit.eventbus.Bus
import guardiantech.com.cn.swedit.eventbus.event.DBChangeEvent


/**
 * Created by liupeiqi on 2017/4/25.
 */
object EventAPI {

    lateinit var eventDao: Dao<EventItem, String>

    fun fetchEventList(callback: (success: Boolean) -> Unit = {}) {
        val request = JsonObjectRequest(Request.Method.GET, Global.API.BASE_URL + "/event/listall", null,
                Response.Listener { response ->
                    val eventArray = response.getJSONArray("content")
                    TableUtils.clearTable(eventDao.connectionSource, EventItem::class.java)
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
                    Bus.post(DBChangeEvent("events"))
                    callback(true)
                },
                Response.ErrorListener {
                    Toast.makeText(Global.context, "An error occured when updating events!", Toast.LENGTH_SHORT).show()
                    callback(false)
                })
        Global.API.queue.add(request)
    }
}