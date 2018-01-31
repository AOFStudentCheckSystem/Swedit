package cn.com.guardiantech.scribe

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.eventbus.EventBus

/**
 * Created by liupeiqi on 2017/4/28.
 */

class Global {
    companion object {
        val bus = EventBus()

        const val DB_NAME = "Swedit.dbHelper"
        const val DB_VERSION = 1

        val mapper = ObjectMapper()
    }
}