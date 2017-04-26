package guardiantech.com.cn.swedit

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import guardiantech.com.cn.swedit.adapters.EventListAdapter
import guardiantech.com.cn.swedit.database.persistence.EventItem
import guardiantech.com.cn.swedit.network.EventAPI
import java.util.*
import android.R.attr.data
import android.util.Log


/**
 * A placeholder fragment containing a simple view.
 */
class EventActivityListFragment : DBFragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var eventDao: Dao<EventItem, String>
    private lateinit var mAdapter: EventListAdapter
    private lateinit var eventAPI: EventAPI
    private lateinit var mSwipeLayout: SwipeRefreshLayout
    private var refreshing = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_event_list, container, false)
        eventDao = dbHelper.eventDao!!
        eventDao.setObjectCache(true)

//        val events = arrayOf(EventItem("1", "title1", "desc1", Date(1490000000000L), 0),
//                EventItem("2", "title2", "desc2", Date(1490000001000L), 0),
//                EventItem("3", "title3", "desc3", Date(1490000002000L), 0))
//        eventDao.createOrUpdate(events[0])
//        eventDao.createOrUpdate(events[1])
//        eventDao.createOrUpdate(events[2])

        mAdapter = EventListAdapter(context, eventDao)
        eventAPI = EventAPI(context, eventDao, mAdapter)
        val listView = rootView.findViewById(R.id.event_list_view) as ListView
        listView.adapter = mAdapter

        mSwipeLayout = rootView.findViewById(R.id.event_list_refresh) as SwipeRefreshLayout
        mSwipeLayout.setOnRefreshListener(this);

        eventAPI.fetchEventList()

        return rootView
    }

    override fun onRefresh() {
        if (!refreshing) {
            refreshing = true
            mSwipeLayout.isRefreshing = true
            eventAPI.fetchEventList {
                refreshing = false
                mSwipeLayout.isRefreshing = false
            }
        }
    }
}
