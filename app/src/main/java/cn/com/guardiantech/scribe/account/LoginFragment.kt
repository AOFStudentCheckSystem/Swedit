package cn.com.guardiantech.scribe.account

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.api.LoadingManager
import cn.com.guardiantech.scribe.controller.AccountController


/**
 * Created by liupeiqi on 2017/4/28.
 */
class LoginFragment : DialogFragment() {
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.login_view, container, false)
        emailField = rootView.findViewById(R.id.login_view_email)
        passwordField = rootView.findViewById(R.id.login_view_password)
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (savedInstanceState !== null) {
            return super.onCreateDialog(savedInstanceState)
        } else {
            val inflater = activity.layoutInflater
            val builder = AlertDialog.Builder(activity)
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