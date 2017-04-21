package guardiantech.com.cn.swedit.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import guardiantech.com.cn.swedit.persistence.EventItem
import android.view.LayoutInflater
import android.widget.BaseAdapter
import guardiantech.com.cn.swedit.R

/**
 * Created by liupeiqi on 2017/4/20.
 */
class EventListAdapter(private val context: Context) : BaseAdapter() {

    val fakeData = listOf("1","2","3","1","2","3","1","2","3","1","2","3","1","2","3")

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        convertView ?: LayoutInflater.from(context).inflate(R.layout.event_list_item, null)

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