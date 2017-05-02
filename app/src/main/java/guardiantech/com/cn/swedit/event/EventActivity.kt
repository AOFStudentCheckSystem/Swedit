package guardiantech.com.cn.swedit.event

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import guardiantech.com.cn.swedit.DBActivity
import guardiantech.com.cn.swedit.R
import guardiantech.com.cn.swedit.account.LoginFragment
import guardiantech.com.cn.swedit.eventbus.event.LoginEvent
import guardiantech.com.cn.swedit.network.APIGlobal
import guardiantech.com.cn.swedit.network.AccountAPI
import guardiantech.com.cn.swedit.network.EventAPI

class EventActivity : DBActivity(),
        EventListFragment.OnEventListSelectedListener,
        EventDetailFragment.OnEventDetailChangeListener {

    private val TAG = "EVENT_ACTIVITY"
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper.eventDao.setObjectCache(true)

        APIGlobal.context = applicationContext
        EventAPI.eventDao = dbHelper.eventDao
        AccountAPI.userDao = dbHelper.userDao

        setContentView(R.layout.activity_event)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (savedInstanceState != null) return
        supportFragmentManager.beginTransaction()
                .add(R.id.event_fragment, EventListFragment()).commit()

        drawer = findViewById(R.id.activity_event_drawer) as DrawerLayout
        toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navHeader = (findViewById(R.id.drawer_navigation) as NavigationView).getHeaderView(0)

        (navHeader.findViewById(R.id.drawer_header_avatar) as ImageView).setOnClickListener {
            onDrawerHeaderClick()
        }

        (navHeader.findViewById(R.id.drawer_header_username) as TextView).setOnClickListener {
            onDrawerHeaderClick()
        }
    }

    private fun onDrawerHeaderClick() {
        LoginFragment().show(fragmentManager, null)
    }

    override fun onLogin (login: LoginEvent) {
        if (login.success) {
            
        } else {
            Toast.makeText(applicationContext, "Login Failed: ${login.error}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        EventAPI.fetchEventList()
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
