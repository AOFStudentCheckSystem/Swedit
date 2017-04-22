package guardiantech.com.cn.swedit.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.TextView
import guardiantech.com.cn.swedit.R

/**
 * Created by liupeiqi on 2017/4/20.
 */
class EventListAdapter(private val context: Context) : BaseAdapter() {

    val fakeData = listOf("1","2","3","1","2","3","1","2","3","1","2","3","1","2","3")

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val newView = convertView ?: LayoutInflater.from(context).inflate(R.layout.event_list_item, null)
        (newView.findViewById(R.id.event_list_item_title) as TextView).text = getItem(position) as String
        (newView.findViewById(R.id.event_list_item_description) as TextView).text = "Desc"
        (newView.findViewById(R.id.event_list_item_time) as TextView).text = "Moment"

        return newView
    }

    override fun getItem(position: Int): Any {
        return fakeData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return 15
    }

}