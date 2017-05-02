package cn.com.guardiantech.scribe.event

import cn.com.guardiantech.scribe.DBFragment
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent
import cn.com.guardiantech.scribe.util.parseEventStatus
import java.util.*

class EventDetailFragment : DBFragment() {
    private lateinit var master: EventDetailFragment.OnEventDetailChangeListener
    private lateinit var event: EventItem

    private lateinit var eventId: android.widget.TextView
    private lateinit var eventName: android.widget.TextView
    private lateinit var eventTime: android.widget.TextView
    private lateinit var eventDescription: android.widget.TextView
    private lateinit var eventStatus: android.widget.TextView

    override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?,
                              savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater!!.inflate(R.layout.fragment_event_detail, container, false)

        event = arguments.getSerializable("event") as EventItem

        eventId = rootView.findViewById(R.id.event_detail_eventId) as android.widget.TextView
        eventName = rootView.findViewById(R.id.event_detail_eventName) as android.widget.TextView
        eventTime = rootView.findViewById(R.id.event_detail_eventTime) as android.widget.TextView
        eventDescription = rootView.findViewById(R.id.event_detail_eventDescription) as android.widget.TextView
        eventStatus = rootView.findViewById(R.id.event_detail_eventStatus) as android.widget.TextView

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
        eventTime.text = java.text.SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss", Locale.US).format(event.eventTime)
        eventDescription.text = event.eventDescription
        eventStatus.text = parseEventStatus(event.eventStatus)
    }

    interface OnEventDetailChangeListener {
        fun onEventDetailEdit()
        fun onEventDetailBack()
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        master = context as OnEventDetailChangeListener
    }

    override fun onDestroy() {
        super.onDestroy()
        master.onEventDetailBack()
    }
}