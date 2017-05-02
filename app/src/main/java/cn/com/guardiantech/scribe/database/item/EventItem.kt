package cn.com.guardiantech.scribe.database.item

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable
import java.util.*

/**
 * Created by liupeiqi on 2017/4/20.
 */
@com.j256.ormlite.table.DatabaseTable(tableName = "events")
class EventItem(): java.io.Serializable {
    @com.j256.ormlite.field.DatabaseField(id = true)
    lateinit var eventId: String
    @com.j256.ormlite.field.DatabaseField
    lateinit var eventName: String
    @com.j256.ormlite.field.DatabaseField
    lateinit var eventDescription: String
    @com.j256.ormlite.field.DatabaseField(dataType = com.j256.ormlite.field.DataType.DATE_LONG)
    lateinit var eventTime: java.util.Date
    @com.j256.ormlite.field.DatabaseField
    var eventStatus: Int = 0

    constructor(eventId: String, eventName: String, eventDescription: String, eventTime: java.util.Date, eventStatus: Int) : this() {
        this.eventId = eventId
        this.eventName = eventName
        this.eventDescription = eventDescription
        this.eventTime = eventTime
        this.eventStatus = eventStatus
    }
}