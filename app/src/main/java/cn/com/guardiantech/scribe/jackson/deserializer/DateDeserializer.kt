package cn.com.guardiantech.scribe.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by dedztbh on 1/7/18.
 * Project AOFGoBackend
 */
class DateDeserializer: JsonDeserializer<Date>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Date {
        val unixTimestamp = p.text.trim()
        return Date(TimeUnit.SECONDS.toMillis(unixTimestamp.toLong()))
    }
}
