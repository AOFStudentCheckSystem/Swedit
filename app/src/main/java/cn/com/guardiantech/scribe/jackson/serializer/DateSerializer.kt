package cn.com.guardiantech.scribe.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by dedztbh on 1/7/18.
 * Project AOFGoBackend
 */
class DateSerializer: JsonSerializer<Date>() {
    override fun serialize(value: Date, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeNumber(TimeUnit.MILLISECONDS.toSeconds(value.time))
    }
}