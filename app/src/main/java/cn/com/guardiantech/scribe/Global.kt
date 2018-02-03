package cn.com.guardiantech.scribe

import android.app.FragmentManager
import android.text.SpannableStringBuilder
import cn.com.guardiantech.scribe.event.EventListFragment
import cn.com.guardiantech.scribe.jackson.deserializer.DateDeserializer
import cn.com.guardiantech.scribe.jackson.serializer.DateSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.google.common.eventbus.EventBus
import java.util.*

/**
 * Created by liupeiqi on 2017/4/28.
 */

class Global {
    companion object {
        val bus = EventBus()

        const val DB_NAME = "Swedit.dbHelper"
        const val DB_VERSION = 1

        val mapper = ObjectMapper().let {
            val module = SimpleModule()
            // Date (DE)Serialization
            module.addDeserializer(Date::class.java, DateDeserializer())
            module.addSerializer(Date::class.java, DateSerializer())
            it.registerModule(module)
            it
        }
    }
}

fun android.widget.EditText.setString(str: String) {
    text = SpannableStringBuilder(str)
}