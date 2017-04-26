package guardiantech.com.cn.swedit.event

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import guardiantech.com.cn.swedit.DBFragment

import guardiantech.com.cn.swedit.R
import guardiantech.com.cn.swedit.database.persistence.EventItem
import guardiantech.com.cn.swedit.eventbus.DBChangeEvent

class EventDetailFragment : DBFragment() {
    private lateinit var master: OnEventDetailChangeListener
    private lateinit var editButton: Button
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

        updateFields()

        editButton = activity.findViewById(R.id.edit_button) as Button
        editButton.text = "Edit"
        editButton.visibility = View.VISIBLE

        return rootView
    }

    override fun onDBUpdate(dbUpdate: DBChangeEvent) {
        if (dbUpdate.tableName == "events") {
            event = dbHelper.eventDao?.queryForId(event.eventId)!!
        }
    }

    fun updateFields () {
        eventId.text = event.eventId
        eventName.text = event.eventName
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
        editButton.text = ""
        editButton.visibility = View.GONE
        master.onEventDetailBack()
    }
}