package cn.com.guardiantech.scribe.database

import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.database.item.EventItem
import cn.com.guardiantech.scribe.database.item.UserItem

/**
 * Created by liupeiqi on 2017/4/24.
 */

class DBHelper(context: android.content.Context) : com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper(context, Global.DB.DB_NAME, null, Global.DB.DB_VERSION) {

    val eventDao by lazy { getDao(EventItem::class.java) as com.j256.ormlite.dao.Dao<EventItem, String> }
    val eventRuntimeDao by lazy { getRuntimeExceptionDao(EventItem::class.java) as com.j256.ormlite.dao.RuntimeExceptionDao<EventItem, String> }
    val userDao by lazy { getDao(UserItem::class.java) as com.j256.ormlite.dao.Dao<UserItem, String> }
    val userRuntimeDao by lazy { getRuntimeExceptionDao(UserItem::class.java) as com.j256.ormlite.dao.RuntimeExceptionDao<UserItem, String> }

    override fun onCreate(database: android.database.sqlite.SQLiteDatabase?, connectionSource: com.j256.ormlite.support.ConnectionSource?) {
        try {
            android.util.Log.i(DBHelper::class.java.name, "onCreate")
            com.j256.ormlite.table.TableUtils.createTable(connectionSource, EventItem::class.java)
        } catch (e: java.sql.SQLException) {
            android.util.Log.e(DBHelper::class.java.name, "Can't create database", e)
            throw RuntimeException(e)
        }
    }

    override fun onUpgrade(database: android.database.sqlite.SQLiteDatabase?, connectionSource: com.j256.ormlite.support.ConnectionSource?, oldVersion: Int, newVersion: Int) {
        try {
            android.util.Log.i(DBHelper::class.java.name, "onUpgrade")
            com.j256.ormlite.table.TableUtils.dropTable<EventItem, String>(connectionSource, EventItem::class.java, true)
            // after we drop the old databases, we create the new ones
            onCreate(database, connectionSource)
        } catch (e: java.sql.SQLException) {
            android.util.Log.e(DBHelper::class.java.name, "Can't drop databases", e)
            throw RuntimeException(e)
        }
    }
}