package cn.com.guardiantech.scribe.event

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.com.guardiantech.scribe.DBActivity
import cn.com.guardiantech.scribe.DBFragment
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent
import cn.com.guardiantech.scribe.util.parseEventStatus
import java.text.SimpleDateFormat
import java.util.*

class EventDetailFragment : DBFragment() {
    private lateinit var master: EventDetailFragment.OnEventDetailChangeListener
    private lateinit var event: EventItem

    private lateinit var eventId: TextView
    private lateinit var eventName: TextView
    private lateinit var eventTime: TextView
    private lateinit var eventDescription: TextView
    private lateinit var eventStatus: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_event_detail, container, false)

        event = arguments?.getSerializable("event") as EventItem

        eventId = rootView.findViewById(R.id.event_detail_eventId)
        eventName = rootView.findViewById(R.id.event_detail_eventName)
        eventTime = rootView.findViewById(R.id.event_detail_eventTime)
        eventDescription = rootView.findViewById(R.id.event_detail_eventDescription)
        eventStatus = rootView.findViewById(R.id.event_detail_eventStatus)

        updateFields()

        return rootView
    }

    override fun onDBUpdate(dbUpdate: DBChangeEvent) {
        if (dbUpdate.tableName == "events") {
            event = (activity as DBActivity).dbHelper.eventDao.queryForId(event.eventId)
            updateFields()
        }
    }

    private fun updateFields() {
        if (::event.isInitialized) {
            eventId.text = event.eventId
            eventName.text = event.eventName
            eventTime.text = SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss", Locale.US).format(event.eventTime)
            eventDescription.text = event.eventDescription
            eventStatus.text = parseEventStatus(event.eventStatus)
        }
    }

    interface OnEventDetailChangeListener {
        fun onEventDetailEdit()
        fun onEventDetailBack()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEventDetailChangeListener) {
            master = context
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::master.isInitialized) {
            master.onEventDetailBack()
        }
    }
}