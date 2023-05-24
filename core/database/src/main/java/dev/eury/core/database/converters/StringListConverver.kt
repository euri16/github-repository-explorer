package dev.eury.core.database.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Types
import dev.eury.core.utils.MoshiParser

class StringListConverter {

    @TypeConverter
    fun fromJson(value: String): List<String>? {
        return MoshiParser.fromJson(
            value, Types.newParameterizedType(List::class.java, String::class.java)
        )
    }

    @TypeConverter
    fun toJson(value: List<String>): String? {
        return MoshiParser.toJson(
            value, Types.newParameterizedType(List::class.java, String::class.java)
        )
    }
}