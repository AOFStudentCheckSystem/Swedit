package cn.com.guardiantech.scribe.event

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import cn.com.guardiantech.scribe.DBActivity
import cn.com.guardiantech.scribe.DBFragment
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.adapters.EventListAdapter
import cn.com.guardiantech.scribe.controller.EventController
import cn.com.guardiantech.scribe.database.entity.ActivityEvent
import cn.com.guardiantech.scribe.eventbus.event.EventsChangeEvent
import com.j256.ormlite.dao.Dao

/**
 * A placeholder fragment containing a simple view.
 */
class EventListFragment : DBFragment(),
        SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener {
    private lateinit var eventDao: Dao<ActivityEvent, String>
    private lateinit var mAdapter: EventListAdapter
    private lateinit var mSwipeLayout: SwipeRefreshLayout
    private lateinit var master: EventListFragment.OnEventListSelectedListener
    private var refreshing = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_event_list, container, false)

        eventDao = (activity as DBActivity).dbHelper.eventDao

        mAdapter = EventListAdapter(activity, eventDao)
        val listView: ListView = rootView.findViewById(R.id.event_list_view)
        listView.adapter = mAdapter
        listView.onItemClickListener = this

        mSwipeLayout = rootView.findViewById(R.id.event_list_refresh)
        mSwipeLayout.setOnRefreshListener(this)
        return rootView
    }

    override fun onRefresh() {
        if (!refreshing) {
            refreshing = true
            mSwipeLayout.isRefreshing = true
            EventController.syncEventList {
                refreshing = false
                mSwipeLayout.isRefreshing = false
            }
        }
    }

    override fun onEventsChange(eventsChangeEvent: EventsChangeEvent) {
        mAdapter.notifyDataSetChanged()
    }

    interface OnEventListSelectedListener {
        fun onEventSelected(eventId: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        master = activity as EventListFragment.OnEventListSelectedListener
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!refreshing) master.onEventSelected(id.toString(36))
    }

}
