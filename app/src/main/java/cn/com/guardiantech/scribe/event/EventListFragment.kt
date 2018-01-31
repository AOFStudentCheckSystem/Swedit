package cn.com.guardiantech.scribe.event

import cn.com.guardiantech.scribe.DBActivity
import cn.com.guardiantech.scribe.DBFragment
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.adapters.EventListAdapter
import cn.com.guardiantech.scribe.controller.EventController
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.eventbus.event.DBChangeEvent

/**
 * A placeholder fragment containing a simple view.
 */
class EventListFragment : DBFragment(),
        android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener,
        android.widget.AdapterView.OnItemClickListener {
    private lateinit var eventDao: com.j256.ormlite.dao.Dao<EventItem, String>
    private lateinit var mAdapter: EventListAdapter
    private lateinit var mSwipeLayout: android.support.v4.widget.SwipeRefreshLayout
    private lateinit var master: EventListFragment.OnEventListSelectedListener
    private var refreshing = false

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
                              savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater.inflate(R.layout.fragment_event_list, container, false)

        eventDao = (activity as DBActivity).dbHelper.eventDao

        mAdapter = EventListAdapter(context, eventDao)
        val listView: android.widget.ListView = rootView.findViewById(R.id.event_list_view)
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
