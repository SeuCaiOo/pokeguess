package br.com.seucaio.pokeguess.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)
