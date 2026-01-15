package br.com.seucaio.pokeguess.data.local

import androidx.room.TypeConverter
import br.com.seucaio.pokeguess.data.local.database.entity.PokemonEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

class Converters {
    private val json = Json {
        allowStructuredMapKeys = true
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.let { json.decodeFromString<List<String>>(it) }
    }

    @TypeConverter
    fun fromMap(map: Map<Int, String>?): String? {
        return map?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun fromStringMap(value: String?): Map<Int, String>? {
        return value?.let { json.decodeFromString<Map<Int, String>>(it) }
    }

    @TypeConverter
    fun fromPokemonMap(map: Map<PokemonEntity, String>?): String? {
        return map?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun fromStringPokemonMap(value: String?): Map<PokemonEntity, String>? {
        return value?.let { json.decodeFromString<Map<PokemonEntity, String>>(it) }
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}
