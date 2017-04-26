package guardiantech.com.cn.swedit.database.persistence

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable
import java.util.*

/**
 * Created by liupeiqi on 2017/4/20.
 */
@DatabaseTable(tableName = "events")
class EventItem(): Serializable {
    @DatabaseField(id = true)
    lateinit var eventId: String
    @DatabaseField
    lateinit var eventName: String
    @DatabaseField
    lateinit var eventDescription: String
    @DatabaseField(dataType = DataType.DATE)
    lateinit var eventTime: Date
    @DatabaseField
    var eventStatus: Int = 0

    constructor(eventId: String, eventName: String, eventDescription: String, eventTime: Date, eventStatus: Int) : this() {
        this.eventId = eventId
        this.eventName = eventName
        this.eventDescription = eventDescription
        this.eventTime = eventTime
        this.eventStatus = eventStatus
    }
}