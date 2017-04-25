package guardiantech.com.cn.swedit.database.persistence

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

/**
 * Created by liupeiqi on 2017/4/20.
 */
@DatabaseTable(tableName = "events")
class EventItem() {
    @DatabaseField(id = true)
    private lateinit var eventId: String
    @DatabaseField
    private lateinit var eventName: String
    @DatabaseField
    private lateinit var eventDescription: String
    @DatabaseField(dataType = DataType.DATE)
    private lateinit var eventTime: Date
    @DatabaseField
    private var eventStatus: Long = 0

    constructor(eventId: String, eventName: String, eventDescription: String, eventTime: Date, eventStatus: Long) : this() {
        this.eventId = eventId
        this.eventName = eventName
        this.eventDescription = eventDescription
        this.eventTime = eventTime
        this.eventStatus = eventStatus
    }
}