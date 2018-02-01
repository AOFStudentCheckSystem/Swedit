package cn.com.guardiantech.scribe.event

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import cn.com.guardiantech.scribe.DBActivity
import cn.com.guardiantech.scribe.DBFragment
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.database.entity.ActivityEvent
import cn.com.guardiantech.scribe.eventbus.event.EventsChangeEvent
import java.text.SimpleDateFormat
import java.util.*

class EventDetailFragment : DBFragment() {
    private lateinit var master: EventDetailFragment.OnEventDetailChangeListener
    private lateinit var event: ActivityEvent

    private lateinit var eventId: TextView
    private lateinit var eventName: TextView
    private lateinit var eventTime: TextView
    private lateinit var eventDescription: TextView
    private lateinit var eventStatus: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_event_detail, container, false)

        event = arguments?.getSerializable("event") as ActivityEvent

        eventId = rootView.findViewById(R.id.event_detail_eventId)
        eventName = rootView.findViewById(R.id.event_detail_eventName)
        eventTime = rootView.findViewById(R.id.event_detail_eventTime)
        eventDescription = rootView.findViewById(R.id.event_detail_eventDescription)
        eventStatus = rootView.findViewById(R.id.event_detail_eventStatus)

        updateFields()

        return rootView
    }

    override fun onEventsChange(eventsChangeEvent: EventsChangeEvent) {
        var delete = true
        (activity as DBActivity).dbHelper.eventDao.queryForId(event.eventId)?.let {
            delete = false
            event = it
            updateFields()
        }
        if (delete) {
            (activity as DBActivity).let {
                Toast.makeText(it.applicationContext, "", Toast.LENGTH_LONG).show()
                it.supportFragmentManager.popBackStack()
            }
        }
    }

    private fun updateFields() {
        if (::event.isInitialized) {
            eventId.text = event.eventId
            eventName.text = event.eventName
            eventTime.text = SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss", Locale.US).format(event.eventTime)
            eventDescription.text = event.eventDescription
            eventStatus.text = event.eventStatus.toString()
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