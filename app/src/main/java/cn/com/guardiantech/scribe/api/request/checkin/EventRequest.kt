package cn.com.guardiantech.scribe.api.request.checkin

import cn.com.guardiantech.scribe.util.NoArg
import java.util.*
import cn.com.guardiantech.scribe.database.entity.EventStatus

/**
 * Created by dedztbh on 1/6/18.
 * Project AOFGoBackend
 */
@NoArg
data class EventRequest(
        val eventId: String?,
        val name: String = "",
        val description: String?,
        val time: Date?,
        val status: EventStatus?
)