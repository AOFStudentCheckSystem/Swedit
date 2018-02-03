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
            callback()
        }

        /**
         * Do not call this function outside this class unless testing
         */
        internal fun syncEventListImpl(success: Boolean, remoteEvents: List<ActivityEvent>) {
            if (success) {
                //All local events here
                val localEvents = eventDao.queryForAll()
                //Database = local(updated) + remote
                remoteEvents.forEach {
                    eventDao.createOrUpdate(it)
                }
                //Here we find local event that does not exists in remote
                val deletedEventIds = localEvents.filter { localEvent ->
                    !remoteEvents.any { remoteEvent ->
                        remoteEvent.eventId == localEvent.eventId
                    }
                }.map {
                            it.eventId
                        }
                //Delete them
                eventDao.deleteIds(deletedEventIds)
                Global.bus.post(EventsChangeEvent())
            }
        }

        fun editEvent(event: ActivityEvent, callback: (success: Boolean, error: String?) -> Unit = { _, _ -> }) {
            API.editEvent(EventRequest(
                    eventId = event.eventId,
                    name = event.eventName,
                    description = event.eventDescription,
                    time = event.eventTime,
                    status = event.eventStatus
            )) { success, error, editedEvent ->
                if (success) {
                    eventDao.createOrUpdate(editedEvent)
                    Global.bus.post(EventsChangeEvent())
                }
                callback(success, error)
            }
        }
    }
}