package cn.com.guardiantech.scribe.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.database.entity.ActivityEvent
import cn.com.guardiantech.scribe.database.entity.EventStatus
import cn.com.guardiantech.scribe.util.DateTimeHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.Where
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by liupeiqi on 2017/4/20.
 */
class EventListAdapter(private val context: Context?, private val eventDao: Dao<ActivityEvent, String>) : BaseAdapter() {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val newView = convertView
                ?: LayoutInflater.from(context).inflate(R.layout.event_list_item, null)
        val event = getItem(position) as ActivityEvent
        (newView.findViewById(R.id.event_list_item_title) as TextView).text = event.eventName
        (newView.findViewById(R.id.event_list_item_description) as TextView).text = event.eventDescription
        (newView.findViewById(R.id.event_list_item_time) as TextView).text = SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss", Locale.US).format(event.eventTime)
        (newView.findViewById(R.id.event_list_item_status) as TextView).text = event.eventStatus.toString()
        return newView
    }

    override fun getItem(position: Int): Any {
        return shownEventsWhere(position).queryForFirst()
    }

    override fun getItemId(position: Int): Long {
        return (getItem(position) as ActivityEvent).eventId.toLong(36)
    }

    override fun getCount(): Int {
        return shownEventsWhere().countOf().toInt()
    }

    private fun shownEventsWhere(position: Int = -1): Where<ActivityEvent, String> {
        val first = position > 0
        val qb = eventDao.queryBuilder()
                .orderBy("eventTime", false)
                .limit(-1L)
        if (first) qb.offset(position.toLong())
        val where = qb.where()
        val isToday = where.and(
                where.gt("eventTime", java.util.Date(DateTimeHelper.firstms())),
                where.lt("eventTime", java.util.Date(DateTimeHelper.lastms())))
        val isNotComplete = where.ne("eventStatus", EventStatus.COMPLETED)
        where.or(isToday, isNotComplete)
        return where
    }
}