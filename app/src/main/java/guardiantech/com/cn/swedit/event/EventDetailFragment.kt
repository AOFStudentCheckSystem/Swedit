package guardiantech.com.cn.swedit.event

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import guardiantech.com.cn.swedit.R
import guardiantech.com.cn.swedit.database.persistence.EventItem

class EventDetailFragment : Fragment() {
    private lateinit var master: OnEventDetailChangeListener
    private lateinit var eventIdTextView: TextView
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_event_detail, container, false)
        eventIdTextView = rootView.findViewById(R.id.event_detail_eventId) as TextView
        eventIdTextView.text = (arguments.getSerializable("event") as EventItem).eventId
        return rootView
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
