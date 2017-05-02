package cn.com.guardiantech.scribe

import cn.com.guardiantech.scribe.database.DBHelper

/**
 * Created by liupeiqi on 2017/4/28.
 */

object Global {

    lateinit var context: android.content.Context

    fun init (context: android.content.Context) {
        cn.com.guardiantech.scribe.Global.context = context
    }

    object DB {
        const val DB_NAME = "Swedit.dbHelper"
        const val DB_VERSION = 1
        val dbHelper by lazy { com.j256.ormlite.android.apptools.OpenHelperManager.getHelper(cn.com.guardiantech.scribe.Global.context, DBHelper::class.java) }
    }

    object API {
        val queue by lazy { com.android.volley.toolbox.Volley.newRequestQueue(cn.com.guardiantech.scribe.Global.context) }
        const val BASE_URL = "https://api.aofactivities.com"
    }
}