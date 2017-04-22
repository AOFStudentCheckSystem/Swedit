package guardiantech.com.cn.swedit

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import guardiantech.com.cn.swedit.adapters.EventListAdapter

/**
 * A placeholder fragment containing a simple view.
 */
class EventActivityFragment : Fragment() {

    lateinit var mAdapter: EventListAdapter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_event, container, false)
        val listView = rootView.findViewById(R.id.event_list_view) as ListView
        mAdapter = EventListAdapter(context)
        listView.adapter = mAdapter
        return rootView
    }


}
