package cn.com.guardiantech.scribe.account

import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.api.LoadingManager
import cn.com.guardiantech.scribe.controller.AccountController


/**
 * Created by liupeiqi on 2017/4/28.
 */
class LoginFragment : android.app.DialogFragment() {
    private lateinit var emailField: android.widget.EditText
    private lateinit var passwordField: android.widget.EditText
    override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater!!.inflate(R.layout.login_view, container, false)
        emailField = rootView.findViewById(R.id.login_view_email)
        passwordField = rootView.findViewById(R.id.login_view_password)
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: android.os.Bundle?): android.app.Dialog {
        if (savedInstanceState !== null) {
            return super.onCreateDialog(savedInstanceState)
        } else {
            val inflater = activity.layoutInflater
            val builder = android.support.v7.app.AlertDialog.Builder(activity)
            builder.setTitle("Please Login")
                    .setView(inflater.inflate(R.layout.login_view, null))
                    .setPositiveButton("Login", { dialog, id ->
                        AccountController.login(emailField.text.toString(), passwordField.text.toString()) {

                        }
                        (activity as LoadingManager).startLoading()
                    })
                    .setNegativeButton("Cancel", { _, _ -> })
            return builder.create()
        }
    }
}