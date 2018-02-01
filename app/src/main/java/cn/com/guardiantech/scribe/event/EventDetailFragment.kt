package cn.com.guardiantech.scribe.event

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import cn.com.guardiantech.scribe.DBActivity
import cn.com.guardiantech.scribe.DBFragment
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.database.entity.ActivityEvent
import cn.com.guardiantech.scribe.dialog.DatePickerFragment
import cn.com.guardiantech.scribe.dialog.TimePickerFragment
import cn.com.guardiantech.scribe.eventbus.event.EventsChangeEvent
import cn.com.guardiantech.scribe.util.setString
import com.google.common.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*


class EventDetailFragment : DBFragment() {
    private lateinit var master: EventDetailFragment.OnEventDetailChangeListener
    private lateinit var event: ActivityEvent

    private lateinit var eventId: TextView
    private lateinit var eventName: EditText
    private lateinit var eventDate: EditText
    private lateinit var eventTime: EditText
    private lateinit var eventDescription: EditText
    private lateinit var eventStatus: TextView

    private var editMode: Boolean = false

    private val TAG = "EventDetailFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_event_detail, container, false)

        eventId = rootView.findViewById(R.id.event_detail_eventId)
        eventName = rootView.findViewById(R.id.event_detail_eventName)
        eventDate = rootView.findViewById(R.id.event_detail_eventDate)
        eventTime = rootView.findViewById(R.id.event_detail_eventTime)
        eventDescription = rootView.findViewById(R.id.event_detail_eventDescription)
        eventStatus = rootView.findViewById(R.id.event_detail_eventStatus)

        if (savedInstanceState == null) {
            event = arguments?.getSerializable("event") as ActivityEvent
            setEditable(true)
        } else {
            event = savedInstanceState.getSerializable("event") as ActivityEvent
            editMode = savedInstanceState.getBoolean("editMode")
        }

        updateFields()

        dateTimeSetup()

        return rootView
    }

    private fun dateTimeSetup() {
        eventDate.isFocusable = false
        eventDate.setOnClickListener {
            if (editMode) {
                val datePicker = DatePickerFragment()
                datePicker.arguments = Bundle().let {
                    it.putSerializable("eventDate", event.eventTime)
                    it.putString("tag", TAG)
                    it
                }
                datePicker.show(fragmentManager, "datePicker")
            }
        }

        eventTime.isFocusable = false
        eventTime.setOnClickListener {
            if (editMode) {
                val timePicker = TimePickerFragment()
                timePicker.arguments = Bundle().let {
                    it.putSerializable("eventTime", event.eventTime)
                    it.putString("tag", TAG)
                    it
                }
                timePicker.show(fragmentManager, "timePicker")
            }
        }
    }

    @Subscribe
    fun onTimeSet(timeSet: TimePickerFragment.TimeSetEvent) {
        if (timeSet.tag == TAG) {
            event.eventTime = timeSet.time
            updateFields()
        }
    }

    @Subscribe
    fun onDateSet(dateSet: DatePickerFragment.DateSetEvent) {
        if (dateSet.tag == TAG) {
            event.eventTime = dateSet.time
            updateFields()
        }
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
                Toast.makeText(it.applicationContext, "This event is gone", Toast.LENGTH_LONG).show()
                it.supportFragmentManager.popBackStack()
            }
        }
    }

    private fun updateFields() {
        if (::event.isInitialized) {
            eventId.text = event.eventId
            eventName.setString(event.eventName)
            eventDate.setString(SimpleDateFormat("yyyy-MM-dd EEE", Locale.US).format(event.eventTime))
            eventTime.setString(SimpleDateFormat("HH:mm", Locale.US).format(event.eventTime))
            eventDescription.setString(event.eventDescription)
            eventStatus.text = event.eventStatus.toString()
        }
    }

    private fun setEditable(editable: Boolean) {
        listOf(eventName, eventDescription).forEach {
            it.isEnabled = editable
        }
        editMode = editable
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

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {
            it.putBoolean("editMode", editMode)
            it.putSerializable("event", event)
        }
        super.onSaveInstanceState(outState)
    }
}