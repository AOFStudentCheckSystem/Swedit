package cn.com.guardiantech.scribe.controller

import cn.com.guardiantech.scribe.api.EventAPI
import cn.com.guardiantech.scribe.database.item.EventItem
import com.j256.ormlite.table.TableUtils

/**
 * Created by liupeiqi on 2017/9/26.
 */
object EventController {
    lateinit var eventDao: com.j256.ormlite.dao.Dao<EventItem, String>

    fun syncEventList(callback: () -> Unit = {}) {
        EventAPI.fetchEventList { success, events ->
            TableUtils.clearTable(eventDao.connectionSource, EventItem::class.java)
            events.forEach {
                eventDao.createOrUpdate(it)
            }
            callback()
        }
    }
}