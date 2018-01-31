package cn.com.guardiantech.scribe.event

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
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent
import com.j256.ormlite.dao.Dao

/**
 * A placeholder fragment containing a simple view.
 */
class EventListFragment : DBFragment(),
        SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener {
    private lateinit var eventDao: Dao<EventItem, String>
    private lateinit var mAdapter: EventListAdapter
    private lateinit var mSwipeLayout: SwipeRefreshLayout
    private lateinit var master: EventListFragment.OnEventListSelectedListener
    private var refreshing = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_event_list, container, false)

        eventDao = (activity as DBActivity).dbHelper.eventDao

        mAdapter = EventListAdapter(context, eventDao)
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

    override fun onDBUpdate(dbUpdate: DBChangeEvent) {
        if (dbUpdate.tableName == "events") {
            mAdapter.notifyDataSetChanged()
        }
    }

    interface OnEventListSelectedListener {
        fun onEventSelected(eventId: String)
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        master = context as EventListFragment.OnEventListSelectedListener
    }

    override fun onItemClick(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
        if (!refreshing) master.onEventSelected(id.toString(36))
    }

}
