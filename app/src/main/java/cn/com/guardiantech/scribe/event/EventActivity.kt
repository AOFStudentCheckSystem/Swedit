package cn.com.guardiantech.scribe.event

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cn.com.guardiantech.scribe.DBActivity
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.Global.DB.dbHelper
import cn.com.guardiantech.scribe.account.LoginFragment
import cn.com.guardiantech.scribe.eventbus.event.LoginEvent
import cn.com.guardiantech.scribe.api.AccountAPI
import cn.com.guardiantech.scribe.api.EventAPI
import cn.com.guardiantech.scribe.api.LoadingManager
import cn.com.guardiantech.scribe.controller.EventController

class EventActivity : DBActivity(),
        EventListFragment.OnEventListSelectedListener,
        EventDetailFragment.OnEventDetailChangeListener,
        LoadingManager {

    private val TAG = "EVENT_ACTIVITY"
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event)
        val toolbar = findViewById(R.id.toolbar) as Toolbar

        if (savedInstanceState === null) {
            setSupportActionBar(toolbar)

            Global.init(applicationContext)

            Global.DB.dbHelper.eventDao.setObjectCache(true)
            Global.DB.dbHelper.userDao.setObjectCache(true)

            EventController.eventDao = Global.DB.dbHelper.eventDao
            AccountAPI.userDao = Global.DB.dbHelper.userDao

            supportFragmentManager.beginTransaction()
                    .add(R.id.event_fragment, EventListFragment()).commit()
        }

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
        stopLoading()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (savedInstanceState === null)
            EventController.syncEventList()
    }

    //Event List
    override fun onEventSelected(eventId: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_enter, R.anim.enter_exit, R.anim.exit_enter, R.anim.exit_exit)
        val fc = EventDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("event", dbHelper.eventDao.queryForId(eventId))
        fc.arguments = bundle
        transaction.replace(R.id.event_fragment, fc)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //Event Detail
    override fun onEventDetailEdit() {}
    override fun onEventDetailBack() {}

    //Loading Manager
    private var loadingDialog: ProgressDialog? = null
    override fun startLoading () {
        if (loadingDialog === null)
            loadingDialog = ProgressDialog.show(this, "", "Loading, Please wait...", true)
    }
    override fun stopLoading () {
        loadingDialog?.let {
            it.dismiss()
            loadingDialog = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        drawer.removeDrawerListener(toggle)
    }
}
