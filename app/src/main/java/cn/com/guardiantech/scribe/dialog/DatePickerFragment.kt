package cn.com.guardiantech.scribe.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.DatePicker
import cn.com.guardiantech.scribe.Global
import java.util.*


/**
 * Created by dedztbh on 18-2-1.
 */
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    class DateSetEvent(
            val time: Date,
            val tag: String
    )

    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var initDate: Date

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments.getSerializable("eventDate")?.let {
            initDate = it as Date
        }

        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        c.time = initDate
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        // Create a new instance of DatePickerDialog and return it
        datePickerDialog = DatePickerDialog(activity, this, year, month, day)
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        Global.bus.post(DateSetEvent(processDate(year, month, day),
                arguments.getString("tag")))
    }

    private fun processDate(year: Int, month: Int, day: Int): Date =
            Calendar.getInstance().let {
                it.time = initDate
                it.set(Calendar.YEAR, year)
                it.set(Calendar.MONTH, month)
                it.set(Calendar.DAY_OF_MONTH, day)
                it.time
            }
}