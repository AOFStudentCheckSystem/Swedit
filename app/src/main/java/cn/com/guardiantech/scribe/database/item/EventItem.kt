package cn.com.guardiantech.scribe.database.item

import cn.com.guardiantech.scribe.util.NoArg
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

/**
 * Created by liupeiqi on 2017/4/20.
 */
@DatabaseTable(tableName = "events")
@NoArg
class EventItem(
        @DatabaseField(id = true)
        var eventId: String,

        @DatabaseField
        var eventName: String,

        @DatabaseField
        var eventDescription: String,

        @DatabaseField(dataType = com.j256.ormlite.field.DataType.DATE_LONG)
        var eventTime: java.util.Date,

        @DatabaseField
        var eventStatus: Int = 0
) : Serializable