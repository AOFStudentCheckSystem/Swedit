package guardiantech.com.cn.swedit.network

import android.app.ProgressDialog

/**
 * Created by liupeiqi on 2017/4/29.
 */
object LoadingManager {
    private var loadingDialog: ProgressDialog? = null

    fun startLoading () {
        if (loadingDialog === null)
            loadingDialog = ProgressDialog.show(APIGlobal.context, "", "Loading, Please wait...", true)
    }

    fun stopLoading () {
        loadingDialog?.let {
            it.dismiss()
            loadingDialog = null
        }
    }
}