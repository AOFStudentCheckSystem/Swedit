package guardiantech.com.cn.swedit.event

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import guardiantech.com.cn.swedit.DBFragment
import guardiantech.com.cn.swedit.Global

import guardiantech.com.cn.swedit.R
import guardiantech.com.cn.swedit.database.item.EventItem
import guardiantech.com.cn.swedit.eventbus.event.DBChangeEvent
import guardiantech.com.cn.swedit.util.parseEventStatus
import java.text.SimpleDateFormat
import java.util.*

class EventDetailFragment : DBFragment() {
    private lateinit var master: OnEventDetailChangeListener
    private lateinit var event: EventItem

    private lateinit var eventId: TextView
    private lateinit var eventName: TextView
    private lateinit var eventTime: TextView
    private lateinit var eventDescription: TextView
    private lateinit var eventStatus: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_event_detail, container, false)

        event = arguments.getSerializable("event") as EventItem

        eventId = rootView.findViewById(R.id.event_detail_eventId) as TextView
        eventName = rootView.findViewById(R.id.event_detail_eventName) as TextView
        eventTime = rootView.findViewById(R.id.event_detail_eventTime) as TextView
        eventDescription = rootView.findViewById(R.id.event_detail_eventDescription) as TextView
        eventStatus = rootView.findViewById(R.id.event_detail_eventStatus) as TextView

        updateFields()

        return rootView
    }

    override fun onDBUpdate(dbUpdate: DBChangeEvent) {
        if (dbUpdate.tableName == "events") {
            event = Global.DB.dbHelper.eventDao.queryForId(event.eventId)
            updateFields()
        }
    }

    fun updateFields () {
        eventId.text = event.eventId
        eventName.text = event.eventName
        eventTime.text = SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss", Locale.US).format(event.eventTime)
        eventDescription.text = event.eventDescription
        eventStatus.text = parseEventStatus(event.eventStatus)
    }

    interface OnEventDetailChangeListener {
        fun onEventDetailEdit()
        fun onEventDetailBack()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        master = context as OnEventDetailChangeListener
    }

    override fun onDestroy() {
        super.onDestroy()
        master.onEventDetailBack()
    }
}