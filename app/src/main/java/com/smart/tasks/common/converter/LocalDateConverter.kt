package com.smart.tasks.common.converter

import androidx.room.TypeConverter
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

class LocalDateConverter : JsonSerializer<LocalDate?>, JsonDeserializer<LocalDate?> {

    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        return FORMATTER.parse(json.asString, LocalDate.FROM)
    }


    @TypeConverter
    fun storedStringToLocalDate(value: String?): LocalDate? {
        return value?.let {
            FORMATTER.parse(value, LocalDate.FROM)
        }
    }

    @TypeConverter
    fun localDateToStoredString(date: LocalDate?): String? {
        return date?.let {
            FORMATTER.format(date)
        }
    }


    companion object {
        private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        val DISPLAY_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy")
    }
}