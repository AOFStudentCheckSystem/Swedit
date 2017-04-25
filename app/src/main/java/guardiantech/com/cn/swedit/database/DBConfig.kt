package guardiantech.com.cn.swedit.database

import android.content.Context
import android.content.res.Resources
import android.util.Log
import guardiantech.com.cn.swedit.R
import java.io.IOException
import java.util.*

/**
 * Created by liupeiqi on 2017/4/24.
 * Copy pasta from Stackoverflow
 */
object DBConfig {
    private val TAG = "DBConfig"

    fun get(context: Context, name: String): String? {
        val resources = context.resources

        try {
            val rawResource = resources.openRawResource(R.raw.db_config)
            val properties = Properties()
            properties.load(rawResource)
            return properties.getProperty(name)
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Unable to find the config file: " + e.message)
        } catch (e: IOException) {
            Log.e(TAG, "Failed to open config file.")
        }

        return null
    }
}