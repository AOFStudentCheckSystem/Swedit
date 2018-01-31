package cn.com.guardiantech.scribe.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.database.entity.ActivityEvent
import cn.com.guardiantech.scribe.database.entity.Session
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.RuntimeExceptionDao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.sql.SQLException

/**
 * Created by liupeiqi on 2017/4/24.
 */

class DBHelper(context: Context) : OrmLiteSqliteOpenHelper(context, Global.DB_NAME, null, Global.DB_VERSION) {

    val eventDao by lazy { getDao(ActivityEvent::class.java) as Dao<ActivityEvent, String> }
    val eventRuntimeDao by lazy { getRuntimeExceptionDao(ActivityEvent::class.java) as RuntimeExceptionDao<ActivityEvent, String> }
    val userDao by lazy { getDao(Session::class.java) as Dao<Session, String> }
    val userRuntimeDao by lazy { getRuntimeExceptionDao(Session::class.java) as RuntimeExceptionDao<Session, String> }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try {
            Log.i(DBHelper::class.java.name, "onCreate")
            TableUtils.createTable(connectionSource, ActivityEvent::class.java)
        } catch (e: SQLException) {
            Log.e(DBHelper::class.java.name, "Can't create database", e)
            throw RuntimeException(e)
        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        try {
            Log.i(DBHelper::class.java.name, "onUpgrade")
            TableUtils.dropTable<ActivityEvent, String>(connectionSource, ActivityEvent::class.java, true)
            // after we drop the old databases, we create the new ones
            onCreate(database, connectionSource)
        } catch (e: SQLException) {
            Log.e(DBHelper::class.java.name, "Can't drop databases", e)
            throw RuntimeException(e)
        }
    }
}