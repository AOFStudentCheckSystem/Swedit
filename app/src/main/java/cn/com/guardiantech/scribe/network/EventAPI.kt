package cn.com.guardiantech.scribe.network

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import cn.com.guardiantech.scribe.database.item.EventItem
import java.util.*
import com.j256.ormlite.table.TableUtils
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent


/**
 * Created by liupeiqi on 2017/4/25.
 */
object EventAPI {

    lateinit var eventDao: com.j256.ormlite.dao.Dao<EventItem, String>

    fun fetchEventList(callback: (success: Boolean) -> Unit = {}) {
        val request = com.android.volley.toolbox.JsonObjectRequest(Request.Method.GET, Global.API.BASE_URL + "/event/listall", null,
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
                    Global.bus.post(DBChangeEvent("events"))
                    callback(true)
                },
                Response.ErrorListener {
                    Toast.makeText(Global.context, "An error occured when updating events!", Toast.LENGTH_SHORT).show()
                    callback(false)
                })
        Global.API.queue.add(request)
    }
}