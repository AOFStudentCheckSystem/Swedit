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
import cn.com.guardiantech.scribe.account.LoginFragment
import cn.com.guardiantech.scribe.api.API
import cn.com.guardiantech.scribe.api.LoadingManager
import cn.com.guardiantech.scribe.controller.AccountController
import cn.com.guardiantech.scribe.controller.EventController
import cn.com.guardiantech.scribe.eventbus.event.LoginEvent

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
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        if (savedInstanceState === null) {
            setSupportActionBar(toolbar)

            dbHelper.eventDao.setObjectCache(true)
            dbHelper.userDao.setObjectCache(true)

            EventController.eventDao = dbHelper.eventDao
            AccountController.userDao = dbHelper.userDao

            API.context = applicationContext

            supportFragmentManager.beginTransaction()
                    .add(R.id.event_fragment, EventListFragment()).commit()
        }

        drawer = findViewById(R.id.activity_event_drawer)
        toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navHeader = findViewById<NavigationView>(R.id.drawer_navigation).getHeaderView(0)

        (navHeader.findViewById<ImageView>(R.id.drawer_header_avatar)).setOnClickListener {
            onDrawerHeaderClick()
        }

        (navHeader.findViewById<TextView>(R.id.drawer_header_username)).setOnClickListener {
            onDrawerHeaderClick()
        }
    }

    private fun onDrawerHeaderClick() {
        LoginFragment().show(fragmentManager, null)
    }

    override fun onLogin(login: LoginEvent) {
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

    override fun startLoading() {
        if (loadingDialog === null)
            loadingDialog = ProgressDialog.show(this, "", "Loading, Please wait...", true)
    }

    override fun stopLoading() {
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
