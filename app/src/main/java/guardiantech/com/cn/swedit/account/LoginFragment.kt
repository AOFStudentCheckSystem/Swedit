package guardiantech.com.cn.swedit.account

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by liupeiqi on 2017/4/28.
 */
class LoginFragment : DialogFragment() {

    private lateinit var loginView: View
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)
        loginView = rootView!!
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (savedInstanceState !== null) {
            return super.onCreateDialog(savedInstanceState)
        } else {
            val builder = AlertDialog.Builder(activity)
            builder.setView(loginView)
                    .setPositiveButton("Login", { dialog, id ->

                    })
                    .setNegativeButton("Cancel", { dialog, id ->
                        // User cancelled the dialog
                    })
            return builder.create()
        }
    }

}