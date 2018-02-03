package cn.com.guardiantech.scribe.event

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cn.com.guardiantech.scribe.DBActivity
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.api.API
import cn.com.guardiantech.scribe.api.LoadingManager
import cn.com.guardiantech.scribe.controller.AccountController
import cn.com.guardiantech.scribe.controller.EventController
import cn.com.guardiantech.scribe.eventbus.event.LoginEvent
import cn.com.guardiantech.scribe.preference.SettingsFragment
import cn.com.guardiantech.scribe.util.setString
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : DBActivity(),
        EventListFragment.OnEventListSelectedListener,
        EventDetailFragment.OnEventDetailChangeListener,
        LoadingManager,
        NavigationView.OnNavigationItemSelectedListener {

    private val TAG = "EVENT_ACTIVITY"
    private lateinit var toggle: ActionBarDrawerToggle

    //Login Dialog
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private var loginDialog: AlertDialog? = null
    private lateinit var usernameDisplay: TextView

    //Loading Manager
    private var loadingDialog: ProgressDialog? = null

    class ToolButtonClick

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set main view
        setContentView(R.layout.activity_event)

        //Add toolbar
        setSupportActionBar(toolbar)

        //Set object cache for DAOs
        dbHelper.eventDao.setObjectCache(true)
        dbHelper.sessionDao.setObjectCache(true)

        //Give controller DAOs
        EventController.eventDao = dbHelper.eventDao
        AccountController.sessionDao = dbHelper.sessionDao

        //Give API context
        API.context = applicationContext

        if (savedInstanceState == null) {
            //Create fragment
            fragmentManager.beginTransaction()
                    .add(R.id.event_fragment, EventListFragment()).commit()

        }

        //Add drawer
        toggle = ActionBarDrawerToggle(
                this, activity_event_drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        activity_event_drawer.addDrawerListener(toggle)
        toggle.syncState()
        drawer_navigation.setNavigationItemSelectedListener(this)

        //Drawer header
        val navHeader = findViewById<NavigationView>(R.id.drawer_navigation).getHeaderView(0)

        (navHeader.findViewById<ImageView>(R.id.drawer_header_avatar)).setOnClickListener {
            onDrawerHeaderClick()
        }

        usernameDisplay = navHeader.findViewById(R.id.drawer_header_username)

        usernameDisplay.setOnClickListener {
            onDrawerHeaderClick()
        }

        //Login State initialization
        AccountController.initLoginState()

        tool_button.setOnClickListener {
            Global.bus.post(ToolButtonClick())
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (savedInstanceState === null && dbHelper.eventDao.count() == 0) {
            if (AccountController.isLoggedIn) {
                EventController.syncEventList()
            } else {
                Toast.makeText(this, "Welcome! Please Login and refresh this event list", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onDrawerHeaderClick() {
        if (loginDialog == null) {
            //Inflate login dialog for later use
            createLoginDialog()
        }
        loginDialog?.show()
    }

    private fun createLoginDialog() {
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

    override fun onLogin(loginEvent: LoginEvent) {
        if (loginEvent.success) {
            usernameDisplay.text = "Welcome back!"
        } else {
            Toast.makeText(applicationContext, "Login Failed: ${loginEvent.error}", Toast.LENGTH_SHORT).show()
            Handler().postDelayed(
                    {
                        if (loginDialog?.isShowing == false) {
                            loginDialog?.show()
                        }
                    },
                    500)

        }
        stopLoading()
    }

    fun setToolButtonText(text: String) {
        if (text.isBlank() && tool_button.text.isNotBlank()) {
            tool_button.visibility = View.GONE
        } else if (text.isNotBlank() && tool_button.text.isBlank()) {
            tool_button.visibility = View.VISIBLE
        }
        tool_button.text = text
    }

    //Menu Selected
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_settings -> {
                Log.v(TAG, "nav_settings clicked")
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.event_fragment, SettingsFragment())
                        .addToBackStack(null)
                        .commit()
            }
            R.id.nav_event_list -> {
                Log.v(TAG, "nav_event_list clicked")
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.event_fragment, EventListFragment())
                        .addToBackStack(null)
                        .commit()
            }
        }
        activity_event_drawer.closeDrawers()
        return true
    }

    //Go to event detail
    override fun onEventSelected(eventId: String) {
        fragmentManager.beginTransaction()
                .replace(R.id.event_fragment, EventDetailFragment().let {
                    it.arguments = Bundle().let {
                        it.putSerializable("event", dbHelper.eventDao.queryForId(eventId))
                        it
                    }
                    it
                })
                .addToBackStack(null)
                .commit()
    }

    //Event Detail Callbacks
    override fun onEventDetailEdit() {}

    override fun onEventDetailBack() {}

    //Loading Manager
    override fun startLoading() {
        if (loadingDialog === null)
            loadingDialog = ProgressDialog.show(this, "", "Loading, Please wait...", true)
    }

    override fun stopLoading() {
        loadingDialog?.let {
            it.hide()
            loadingDialog = null
        }
    }

    //TODO: Save login/loading data after rotation
    override fun onStop() {
        loginDialog?.let {
            it.dismiss()
            loginDialog = null
        }
        loadingDialog?.let {
            it.dismiss()
            loadingDialog = null
        }
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity_event_drawer.removeDrawerListener(toggle)
    }
}
