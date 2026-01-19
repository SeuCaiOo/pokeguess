package br.com.seucaio.pokeguess.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "rounds",
    foreignKeys = [
        ForeignKey(
            entity = GameMatchEntity::class,
            parentColumns = ["game_id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemon_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RoundEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "round_id")
    val roundId: Int = 0,
    @ColumnInfo(name = "game_id")
    val gameId: Int,
    @ColumnInfo(name = "pokemon_id")
    val pokemonId: Int,
    @ColumnInfo(name = "round_number")
    val roundNumber: Int,
    @ColumnInfo(name = "user_guess")
    val userGuess: String = "",
    @ColumnInfo(name = "is_correct")
    val isCorrect: Boolean = false
)
