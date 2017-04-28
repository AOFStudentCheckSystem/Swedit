package guardiantech.com.cn.swedit.event

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import guardiantech.com.cn.swedit.DBActivity
import guardiantech.com.cn.swedit.R
import guardiantech.com.cn.swedit.network.EventAPI

class EventActivity : DBActivity(), EventListFragment.OnEventListSelectedListener, EventDetailFragment.OnEventDetailChangeListener, View.OnClickListener{

    private val TAG = "EVENT_ACTIVITY"
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper.eventDao.setObjectCache(true)

        EventAPI.context = applicationContext
        EventAPI.eventDao = dbHelper.eventDao

        setContentView(R.layout.activity_event)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (savedInstanceState != null) return
        supportFragmentManager.beginTransaction()
                .add(R.id.event_fragment, EventListFragment()).commit()

        val drawer = findViewById(R.id.activity_event_drawer) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.menu_button -> {
//                Log.wtf(TAG, "Yes")
//                val g = Gravity.START
//                if (drawer.isDrawerOpen(g)) drawer.closeDrawer(g)
//                else drawer.openDrawer(g)
//            }
        }
    }

    override fun onEventSelected(eventId: String) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.enter_enter, R.anim.enter_exit, R.anim.exit_enter, R.anim.exit_exit)
//        val fc = EventDetailFragment()
//        val bundle = Bundle()
//        bundle.putSerializable("event", dbHelper.eventDao.queryForId(eventId))
//        fc.arguments = bundle
//        transaction.replace(R.id.event_fragment, fc)
//        transaction.addToBackStack(null)
//        transaction.commit()
    }

    override fun onEventDetailEdit() {}

    override fun onEventDetailBack() {}

    override fun onDestroy() {
        super.onDestroy()
        drawer.removeDrawerListener(toggle)
    }
}