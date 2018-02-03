package cn.com.guardiantech.scribe.controller

import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.api.API
import cn.com.guardiantech.scribe.api.request.checkin.EventRequest
import cn.com.guardiantech.scribe.database.entity.ActivityEvent
import cn.com.guardiantech.scribe.eventbus.event.EventsChangeEvent
import com.j256.ormlite.dao.Dao

/**
 * Created by liupeiqi on 2017/9/26.
 */
class EventController {
    companion object {
        lateinit var eventDao: Dao<ActivityEvent, String>

        fun syncEventList(callback: () -> Unit = {}) {
            API.fetchEventList(::syncEventListImpl)
            Global.bus.post(EventsChangeEvent())
            callback()
        }

        /**
         * Do not call this function outside this class unless testing
         */
        internal fun syncEventListImpl(success: Boolean, remoteEvents: List<ActivityEvent>) {
            val localEvents = eventDao.queryForAll()
            remoteEvents.forEach {
                eventDao.createOrUpdate(it)
            }
            val deletedEventIds = localEvents.filter {
                !remoteEvents.any { remoteEvent -> remoteEvent.eventId === it.eventId }
            }.map {
                        it.eventId
                    }
            eventDao.deleteIds(deletedEventIds)
        }

        fun editEvent(event: ActivityEvent, callback: () -> Unit = {}) {
            API.editEvent(EventRequest(
                    eventId = event.eventId,
                    name = event.eventName,
                    description = event.eventDescription,
                    time = event.eventTime,
                    status = event.eventStatus
            )) { success, error, editedEvent ->

            }
        }
    }
}