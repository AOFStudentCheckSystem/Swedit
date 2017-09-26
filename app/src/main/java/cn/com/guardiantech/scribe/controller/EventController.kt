package cn.com.guardiantech.scribe.controller

import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.api.EventAPI
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent

/**
 * Created by liupeiqi on 2017/9/26.
 */
object EventController {
    lateinit var eventDao: com.j256.ormlite.dao.Dao<EventItem, String>

    fun syncEventList(callback: () -> Unit = {}) {
        EventAPI.fetchEventList { success, remoteEvents ->
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