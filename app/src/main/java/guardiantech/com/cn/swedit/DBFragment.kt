package guardiantech.com.cn.swedit

import android.support.v4.app.Fragment
import android.os.Bundle
import com.j256.ormlite.android.apptools.OpenHelperManager
import guardiantech.com.cn.swedit.database.DBHelper

/**
 * Created by liupeiqi on 2017/4/25.
 */
open class DBFragment: Fragment() {
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = OpenHelperManager.getHelper(context, DBHelper::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        OpenHelperManager.releaseHelper()
    }
}