package cn.com.guardiantech.scribe.controller

import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.api.API
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent
import com.j256.ormlite.dao.Dao

/**
 * Created by liupeiqi on 2017/9/26.
 */
class EventController {
    companion object {
        lateinit var eventDao: Dao<EventItem, String>

        fun syncEventList(callback: () -> Unit = {}) {
            API.fetchEventList { success, remoteEvents ->
                //            TableUtils.clearTable(eventDao.connectionSource, EventItem::class.java)
                val localEvents = eventDao.queryForAll()
                remoteEvents.forEach {
                    eventDao.createOrUpdate(it)
                }
                val deletedEventIds = localEvents.filter {
                    var found = false
                    for (remoteEvent in remoteEvents) {
                        if (remoteEvent.eventId === it.eventId) {
                            found = true
                            break
                        }
                    }
                    found
                }.map {
                            it.eventId
                        }
                eventDao.deleteIds(deletedEventIds)
                Global.bus.post(DBChangeEvent("events"))
                callback()
            }
        }
    }
}