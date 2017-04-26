package guardiantech.com.cn.swedit.event

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.j256.ormlite.dao.Dao
import guardiantech.com.cn.swedit.DBFragment
import guardiantech.com.cn.swedit.R
import guardiantech.com.cn.swedit.adapters.EventListAdapter
import guardiantech.com.cn.swedit.database.persistence.EventItem
import guardiantech.com.cn.swedit.network.EventAPI

/**
 * A placeholder fragment containing a simple view.
 */
class EventListFragment : DBFragment(), SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private lateinit var eventDao: Dao<EventItem, String>
    private lateinit var mAdapter: EventListAdapter
    private lateinit var mSwipeLayout: SwipeRefreshLayout
    private lateinit var master: OnEventListSelectedListener
    private var refreshing = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_event_list, container, false)

        eventDao = dbHelper.eventDao!!

        mAdapter = EventListAdapter(context, eventDao)
        EventAPI.eventListAdapter = mAdapter
        val listView = rootView.findViewById(R.id.event_list_view) as ListView
        listView.adapter = mAdapter
        listView.onItemClickListener = this

        mSwipeLayout = rootView.findViewById(R.id.event_list_refresh) as SwipeRefreshLayout
        mSwipeLayout.setOnRefreshListener(this)
        return rootView
    }

    override fun onRefresh() {
        if (!refreshing) {
            refreshing = true
            mSwipeLayout.isRefreshing = true
            EventAPI.fetchEventList {
                refreshing = false
                mSwipeLayout.isRefreshing = false
            }
        }
    }

    interface OnEventListSelectedListener {
        fun onEventSelected(eventId: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        master = context as OnEventListSelectedListener
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!refreshing) master.onEventSelected(id.toString(36))
    }

}
