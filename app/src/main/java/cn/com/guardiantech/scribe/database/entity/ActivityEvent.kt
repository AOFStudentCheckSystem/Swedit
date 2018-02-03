package cn.com.guardiantech.scribe.database.entity

import cn.com.guardiantech.scribe.util.NoArg
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * Created by liupeiqi on 2017/4/20.
 */
@Entity(name = "activity_event")
@NoArg
@JsonIgnoreProperties(ignoreUnknown = true)
class ActivityEvent(
        @Id
        @Column
        var eventId: String,

        @Column
        var eventName: String = "",

        @Lob
        @Column
        var eventDescription: String = "",

        @Column
        var eventTime: Date = Date(),

        @Enumerated(value = EnumType.STRING)
        @Column
        var eventStatus: EventStatus = EventStatus.FUTURE
) : Serializable