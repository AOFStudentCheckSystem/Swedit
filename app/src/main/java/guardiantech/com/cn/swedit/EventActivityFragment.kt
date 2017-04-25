package guardiantech.com.cn.swedit

import android.support.v4.app.Fragment
import android.os.Bundle
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

/**
 * A placeholder fragment containing a simple view.
 */
class EventActivityFragment : DBFragment() {
    private lateinit var eventDao: Dao<EventItem, String>
    private lateinit var mAdapter: EventListAdapter
    private lateinit var eventAPI: EventAPI

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_event, container, false)
        val listView = rootView.findViewById(R.id.event_list_view) as ListView

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

        listView.adapter = mAdapter

        eventAPI.fetchEventList()

        return rootView
    }
}
