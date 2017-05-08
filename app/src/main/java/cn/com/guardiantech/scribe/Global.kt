package cn.com.guardiantech.scribe

import android.content.Context
import cn.com.guardiantech.scribe.database.DBHelper
import com.android.volley.toolbox.Volley
import com.google.common.eventbus.EventBus
import com.j256.ormlite.android.apptools.OpenHelperManager

/**
 * Created by liupeiqi on 2017/4/28.
 */

object Global {

    lateinit var context: Context
    val bus = EventBus()

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