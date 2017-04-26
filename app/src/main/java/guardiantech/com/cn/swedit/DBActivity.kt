package guardiantech.com.cn.swedit

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.j256.ormlite.android.apptools.OpenHelperManager
import guardiantech.com.cn.swedit.database.DBHelper

/**
 * Created by liupeiqi on 2017/4/26.
 */

open class DBActivity : AppCompatActivity() {
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = OpenHelperManager.getHelper(applicationContext, DBHelper::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        OpenHelperManager.releaseHelper()
    }
}