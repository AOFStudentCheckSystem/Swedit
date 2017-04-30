package guardiantech.com.cn.swedit.account

import android.accounts.Account
import android.app.Dialog
import android.app.DialogFragment
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import guardiantech.com.cn.swedit.R
import guardiantech.com.cn.swedit.eventbus.Bus
import guardiantech.com.cn.swedit.eventbus.event.LoginEvent
import guardiantech.com.cn.swedit.network.AccountAPI
import java.util.concurrent.CountDownLatch


/**
 * Created by liupeiqi on 2017/4/28.
 */
class LoginFragment : DialogFragment() {

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)
        emailField = rootView.findViewById(R.id.login_view_email) as EditText
        passwordField = rootView.findViewById(R.id.login_view_password) as EditText
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
                        AccountAPI.login(emailField.text.toString(), passwordField.text.toString()) { success, error ->
                            Bus.post(LoginEvent(success, error?:"Unknown"))
                        }
                    })
                    .setNegativeButton("Cancel", { _, _ -> })
            return builder.create()
        }
    }
}