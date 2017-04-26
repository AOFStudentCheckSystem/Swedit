package guardiantech.com.cn.swedit.event

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import guardiantech.com.cn.swedit.DBActivity
import guardiantech.com.cn.swedit.R
import guardiantech.com.cn.swedit.network.EventAPI
import java.io.Serializable

class EventActivity : DBActivity(), EventListFragment.OnEventListSelectedListener, EventDetailFragment.OnEventDetailChangeListener {
    private val TAG = "EVENT_ACTIVITY"

    private lateinit var editButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper.eventDao?.setObjectCache(true)

        EventAPI.context = applicationContext
        EventAPI.eventDao = dbHelper.eventDao!!

        setContentView(R.layout.activity_event)

        editButton = findViewById(R.id.edit_button) as Button
        editButton.visibility = View.GONE
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (savedInstanceState != null) return
        supportFragmentManager.beginTransaction()
                .add(R.id.event_fragment, EventListFragment()).commit()
    }

    override fun onEventSelected(eventId: String) {
//        Log.wtf(TAG , eventId)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_enter, R.anim.enter_exit, R.anim.exit_enter, R.anim.exit_exit)
        val fc = EventDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("event", dbHelper.eventDao?.queryForId(eventId))
        fc.arguments = bundle
        transaction.replace(R.id.event_fragment, fc)
        transaction.addToBackStack(null)
        transaction.commit()
        editButton.visibility = View.VISIBLE
    }

    override fun onEventDetailEdit() {}

    override fun onEventDetailBack() {
        editButton.visibility = View.GONE
    }
}
