package cn.com.guardiantech.scribe.database.item

import cn.com.guardiantech.scribe.util.NoArg

/**
 * Created by liupeiqi on 2017/4/20.
 */
@com.j256.ormlite.table.DatabaseTable(tableName = "events")
@NoArg
class EventItem(
        @com.j256.ormlite.field.DatabaseField(id = true)
        var eventId: String,

        @com.j256.ormlite.field.DatabaseField
        var eventName: String,

        @com.j256.ormlite.field.DatabaseField
        var eventDescription: String,

        @com.j256.ormlite.field.DatabaseField(dataType = com.j256.ormlite.field.DataType.DATE_LONG)
        var eventTime: java.util.Date,

        @com.j256.ormlite.field.DatabaseField
        var eventStatus: Int = 0
) : java.io.Serializable