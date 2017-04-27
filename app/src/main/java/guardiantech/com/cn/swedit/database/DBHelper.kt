package guardiantech.com.cn.swedit.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.dao.RuntimeExceptionDao
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.table.TableUtils
import guardiantech.com.cn.swedit.database.persistence.EventItem
import java.sql.SQLException


/**
 * Created by liupeiqi on 2017/4/24.
 */

const val DB_NAME = "Swedit.db"
const val DB_VERSION = 1
class DBHelper(context: Context) : OrmLiteSqliteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    val eventDao by lazy { getDao(EventItem::class.java) as Dao<EventItem, String> }
    val eventRuntimeDao by lazy { getRuntimeExceptionDao(EventItem::class.java) as RuntimeExceptionDao<EventItem, String> }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try {
            Log.i(DBHelper::class.java.name, "onCreate")
            TableUtils.createTable(connectionSource, EventItem::class.java)
        } catch (e: SQLException) {
            Log.e(DBHelper::class.java.name, "Can't create database", e)
            throw RuntimeException(e)
        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        try {
            Log.i(DBHelper::class.java.name, "onUpgrade")
            TableUtils.dropTable<EventItem, String>(connectionSource, EventItem::class.java, true)
            // after we drop the old databases, we create the new ones
            onCreate(database, connectionSource)
        } catch (e: SQLException) {
            Log.e(DBHelper::class.java.name, "Can't drop databases", e)
            throw RuntimeException(e)
        }
    }
}