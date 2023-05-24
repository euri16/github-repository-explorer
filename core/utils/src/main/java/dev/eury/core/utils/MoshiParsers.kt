package dev.eury.core.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

object MoshiParser {

    private fun <T> getAdapter(type: Type) = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
        .adapter<T>(type)

    fun <T> fromJson(jsonString: String, type: Type): T? {
        return getAdapter<T>(type).fromJson(jsonString)
    }

    fun <T> toJson(obj: T, type: Type): String? {
        return getAdapter<T>(type).toJson(obj)
    }

}