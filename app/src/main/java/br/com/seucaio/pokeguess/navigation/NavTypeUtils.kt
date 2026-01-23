package br.com.seucaio.pokeguess.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

object NavTypeUtils {
    inline fun <reified T : Any> serializableNavType(
        isNullableAllowed: Boolean = false,
        json: Json = Json
    ) = object : NavType<T>(isNullableAllowed = true) {
        override fun get(bundle: Bundle, key: String): T? {
            return bundle.getString(key)?.let { json.decodeFromString(it) }
        }

        override fun parseValue(value: String): T {
            return json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putString(key, json.encodeToString(value))
        }

        override fun serializeAsValue(value: T): String {
            return Uri.encode(json.encodeToString(value))
        }
    }

    inline fun <reified T : Any> typeMapOf() = mapOf(typeOf<T>() to serializableNavType<T>())
}
