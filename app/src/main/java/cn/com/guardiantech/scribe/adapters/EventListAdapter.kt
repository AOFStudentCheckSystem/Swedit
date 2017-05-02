package cn.com.guardiantech.scribe.adapters

import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.util.DateTimeHelper
import cn.com.guardiantech.scribe.util.parseEventStatus
import java.util.*


/**
 * Created by liupeiqi on 2017/4/20.
 */
class EventListAdapter(private val context: android.content.Context, private val eventDao: com.j256.ormlite.dao.Dao<EventItem, String>) : android.widget.BaseAdapter() {

    @android.annotation.SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup?): android.view.View {
        val newView = convertView ?: android.view.LayoutInflater.from(context).inflate(R.layout.event_list_item, null)
        val event = getItem(position) as EventItem
        (newView.findViewById(R.id.event_list_item_title) as android.widget.TextView).text = event.eventName
        (newView.findViewById(R.id.event_list_item_description) as android.widget.TextView).text = event.eventDescription
        (newView.findViewById(R.id.event_list_item_time) as android.widget.TextView).text = java.text.SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss", Locale.US).format(event.eventTime)
        (newView.findViewById(R.id.event_list_item_status) as android.widget.TextView).text = parseEventStatus(event.eventStatus)
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

    fun shownEventsWhere (position: Int = -1): com.j256.ormlite.stmt.Where<EventItem, String> {
        val first = position > 0
        val qb = eventDao.queryBuilder()
                .orderBy("eventTime", false)
                .limit(-1L)
        if (first) qb.offset(position.toLong())
        val where = qb.where()
        where.or(
                where.and(
                        where.gt("eventTime", java.util.Date(DateTimeHelper.firstms())),
                        where.lt("eventTime", java.util.Date(DateTimeHelper.lastms()))),
                where.lt("eventStatus", 2)
        )
        return where
    }
}