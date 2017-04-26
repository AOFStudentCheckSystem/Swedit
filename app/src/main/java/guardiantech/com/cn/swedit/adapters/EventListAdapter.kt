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
import guardiantech.com.cn.swedit.database.persistence.EventItem
import java.text.SimpleDateFormat
import java.util.*

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
        (newView.findViewById(R.id.event_list_item_status) as TextView).text = parseStatus(event.eventStatus)
        return newView
    }

    override fun getItem(position: Int): Any {
        return eventDao.queryForFirst(eventDao.queryBuilder().orderBy("eventTime", false).offset(position.toLong()).limit(1L).prepare())
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return eventDao.countOf().toInt()
    }

    private fun parseStatus(num: Int): String {
        when (num) {
            0 -> return "Scheduled"
            1 -> return "Boarding"
            2 -> return "Complete"
            else -> return "Unknown"
        }
    }

}