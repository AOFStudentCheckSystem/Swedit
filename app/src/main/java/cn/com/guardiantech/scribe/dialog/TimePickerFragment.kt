package cn.com.guardiantech.scribe.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import cn.com.guardiantech.scribe.Global
import java.util.*

/**
 * Created by dedztbh on 18-2-1.
 */
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    class TimeSetEvent (
            val time: Date,
            val tag: String
    )

    private lateinit var timePickerDialog: TimePickerDialog

    private lateinit var initDate: Date

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments.getSerializable("eventTime")?.let {
            initDate = it as Date
        }

        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        c.time = initDate
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        // Create a new instance of TimePickerDialog and return it
        timePickerDialog = TimePickerDialog(activity, this, hour, minute,
                DateFormat.is24HourFormat(activity))
        return timePickerDialog
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        Global.bus.post(TimeSetEvent(processDate(hourOfDay, minute),
                arguments.getString("tag")))
    }

    private fun processDate(hourOfDay: Int, minute: Int): Date =
            Calendar.getInstance().let {
                it.time = initDate
                it.set(Calendar.HOUR_OF_DAY, hourOfDay)
                it.set(Calendar.MINUTE, minute)
                it.time
            }
}