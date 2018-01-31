package cn.com.guardiantech.scribe.event

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cn.com.guardiantech.scribe.DBActivity
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.api.API
import cn.com.guardiantech.scribe.api.LoadingManager
import cn.com.guardiantech.scribe.controller.AccountController
import cn.com.guardiantech.scribe.controller.EventController
import cn.com.guardiantech.scribe.eventbus.event.LoginEvent
import cn.com.guardiantech.scribe.util.setString

class EventActivity : DBActivity(),
        EventListFragment.OnEventListSelectedListener,
        EventDetailFragment.OnEventDetailChangeListener,
        LoadingManager {

    private val TAG = "EVENT_ACTIVITY"
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginDialog: AlertDialog
    private lateinit var usernameDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        if (savedInstanceState === null) {
            setSupportActionBar(toolbar)

            dbHelper.eventDao.setObjectCache(true)
            dbHelper.sessionDao.setObjectCache(true)

            EventController.eventDao = dbHelper.eventDao
            AccountController.sessionDao = dbHelper.sessionDao

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

        usernameDisplay = navHeader.findViewById(R.id.drawer_header_username)

        usernameDisplay.setOnClickListener {
            onDrawerHeaderClick()
        }
    }

    private fun onDrawerHeaderClick() {
        if (!::loginDialog.isInitialized) {
            val loginLayout = layoutInflater.inflate(R.layout.login_view, null, false)
            emailField = loginLayout.findViewById(R.id.login_view_email)
            passwordField = loginLayout.findViewById(R.id.login_view_password)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Please Login")
                    .setView(loginLayout)
                    .setPositiveButton("Login", { dialog, id ->
                        AccountController.login(emailField.text.toString(), passwordField.text.toString()) { success ->
                            passwordField.setString("")
                            if (success) {
                                emailField.setString("")
                            }
                        }
                        startLoading()
                    })
                    .setNegativeButton("Cancel", { _, _ -> })
            loginDialog = builder.create()
        }
        loginDialog.show()
    }

    override fun onLogin(login: LoginEvent) {
        if (login.success) {
            usernameDisplay.text = "Welcome back!"
        } else {
            Toast.makeText(applicationContext, "Login Failed: ${login.error}", Toast.LENGTH_SHORT).show()
            Handler().postDelayed(
                    {
                        if (!loginDialog.isShowing) {
                            loginDialog.show()
                        }
                    },
                    500)

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
