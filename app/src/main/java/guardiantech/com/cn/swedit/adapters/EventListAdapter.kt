package guardiantech.com.cn.swedit.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.TextView
import com.j256.ormlite.dao.Dao
import guardiantech.com.cn.swedit.R
import guardiantech.com.cn.swedit.database.item.EventItem
import guardiantech.com.cn.swedit.util.DateTimeHelper
import guardiantech.com.cn.swedit.util.parseEventStatus
import java.text.SimpleDateFormat
import java.util.*
import com.j256.ormlite.stmt.Where


/**
 * Created by liupeiqi on 2017/4/20.
 */
class EventListAdapter(private val context: Context, private val eventDao: Dao<EventItem, String>) : BaseAdapter() {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val newView = convertView ?: LayoutInflater.from(context).inflate(R.layout.event_list_item, null)
        val event = getItem(position) as EventItem
        (newView.findViewById(R.id.event_list_item_title) as TextView).text = event.eventName
        (newView.findViewById(R.id.event_list_item_description) as TextView).text = event.eventDescription
        (newView.findViewById(R.id.event_list_item_time) as TextView).text = SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss", Locale.US).format(event.eventTime)
        (newView.findViewById(R.id.event_list_item_status) as TextView).text = parseEventStatus(event.eventStatus)
        return newView
    }

    override fun getItem(position: Int): Any {
        return shownEventsWhere(position).queryForFirst()
    }

    override fun getItemId(position: Int): Long {
        return (getItem(position) as EventItem).eventId.toLong(36)
    }

    override fun getCount(): Int {
        return shownEventsWhere().countOf().toInt()
    }

    fun shownEventsWhere (position: Int = -1): Where<EventItem, String> {
        val first = position > 0
        val qb = eventDao.queryBuilder()
                .orderBy("eventTime", false)
                .limit(-1L)
        if (first) qb.offset(position.toLong())
        val where = qb.where()
        where.or(
                where.and(
                        where.gt("eventTime", Date(DateTimeHelper.firstms())),
                        where.lt("eventTime", Date(DateTimeHelper.lastms()))),
                where.lt("eventStatus", 2)
        )
        return where
    }
}