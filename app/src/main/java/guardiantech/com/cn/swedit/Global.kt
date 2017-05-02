package guardiantech.com.cn.swedit

import android.content.Context
import com.android.volley.toolbox.Volley
import com.j256.ormlite.android.apptools.OpenHelperManager
import guardiantech.com.cn.swedit.database.DBHelper

/**
 * Created by liupeiqi on 2017/4/28.
 */

object Global {

    lateinit var context: Context

    fun init (context: Context) {
        this.context = context
    }

    object DB {
        const val DB_NAME = "Swedit.dbHelper"
        const val DB_VERSION = 1
        val dbHelper by lazy { OpenHelperManager.getHelper(context, DBHelper::class.java) }
    }

    object API {
        val queue by lazy { Volley.newRequestQueue(context) }
        const val BASE_URL = "https://api.aofactivities.com"
    }
}