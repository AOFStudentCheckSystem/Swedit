package cn.com.guardiantech.scribe.event

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import cn.com.guardiantech.scribe.DBFragment
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.controller.EventController
import cn.com.guardiantech.scribe.database.entity.ActivityEvent
import cn.com.guardiantech.scribe.dialog.DatePickerFragment
import cn.com.guardiantech.scribe.dialog.TimePickerFragment
import cn.com.guardiantech.scribe.eventbus.event.EventsChangeEvent
import cn.com.guardiantech.scribe.setString
import com.google.common.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*


class EventDetailFragment : DBFragment() {
    private lateinit var event: ActivityEvent

    private lateinit var eventId: TextView
    private lateinit var eventName: EditText
    private lateinit var eventDate: TextView
    private lateinit var eventTime: TextView
    private lateinit var eventDescription: EditText
    private lateinit var eventStatus: TextView

    private var editMode: Boolean = false

    override val TAG = "EventDetailFragment"

    interface OnEventDetailChangeListener {
        fun onEventDetailBack()
    }

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
            setEditable(false)
        } else {
            event = savedInstanceState.getSerializable("event") as ActivityEvent
            setEditable(savedInstanceState.getBoolean("editMode"))
        }

        updateFields()

        setEditTextListener()

        dateTimeSetup()

        return rootView
    }

    private fun dateTimeSetup() {
        eventDate.setOnClickListener {
            if (editMode) {
                temporaryDisableKeyboard()
                val datePicker = DatePickerFragment()
                datePicker.arguments = Bundle().let {
                    it.putSerializable("eventDate", event.eventTime)
                    it.putString("tag", TAG)
                    it
                }
                datePicker.show(fragmentManager, "datePicker")
            }
        }

        eventTime.setOnClickListener {
            if (editMode) {
                temporaryDisableKeyboard()
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

    private fun setEditTextListener() {
        eventName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                event.eventName = s.toString()
            }
        })

        eventDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                event.eventDescription = s.toString()
            }
        })
    }

    @Subscribe
    fun onTimeSet(timeSet: TimePickerFragment.TimeSetEvent) {
        if (timeSet.tag == TAG) {
            event.eventTime = timeSet.time
            updateFields()
            temporaryDisableKeyboard(true)
        }
    }

    @Subscribe
    fun onDateSet(dateSet: DatePickerFragment.DateSetEvent) {
        if (dateSet.tag == TAG) {
            event.eventTime = dateSet.time
            updateFields()
            temporaryDisableKeyboard(true)
        }
    }

    @Subscribe
    fun onToolButtonClick(click: EventActivity.ToolButtonClick) {
        if (editMode) {
            EventController.editEvent(event) { success, error ->
                (activity as EventActivity).stopLoading()
                if (success) {
                    setEditable(false)
                } else {
                    Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                }
            }
            (activity as EventActivity).startLoading()
        } else {
            setEditable(true)
        }
    }

    override fun onEventsChange(eventsChangeEvent: EventsChangeEvent) {
        var delete = true
        (activity as EventActivity).dbHelper.eventDao.queryForId(event.eventId)?.let {
            delete = false
            event = it
            updateFields()
        }
        if (delete) {
            (activity as EventActivity).let {
                Toast.makeText(it.applicationContext, "This event is gone", Toast.LENGTH_SHORT).show()
                it.supportFragmentManager.popBackStack()
            }
        }
    }

    private fun updateFields() {
        if (::event.isInitialized) {
            eventId.text = event.eventId
            eventName.setString(event.eventName)
            eventDate.text = SimpleDateFormat("yyyy-MM-dd EEE", Locale.US).format(event.eventTime)
            eventTime.text = SimpleDateFormat("HH:mm", Locale.US).format(event.eventTime)
            eventDescription.setString(event.eventDescription)
            eventStatus.text = event.eventStatus.toString()
        }
    }

    private fun setEditable(editable: Boolean) {
        listOf(eventName, eventDescription).forEach {
            it.isEnabled = editable
        }
        editMode = editable
        (activity as EventActivity).setToolButtonText(if (editable) "Save" else "Edit")
    }

    private fun temporaryDisableKeyboard(recover: Boolean = false) {
        if (editMode) {
            eventName.isEnabled = recover
            eventDescription.isEnabled = recover
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as EventActivity).onEventDetailBack()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {
            it.putBoolean("editMode", editMode)
            it.putSerializable("event", event)
        }
        super.onSaveInstanceState(outState)
    }
}