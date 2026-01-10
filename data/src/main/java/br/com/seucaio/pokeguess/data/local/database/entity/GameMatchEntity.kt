package br.com.seucaio.pokeguess.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_matchs",
    indices = [Index(value = ["game_id"], unique = true)]
)
data class GameMatchEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    val gameId: Int? = 0,
    @ColumnInfo(name = "player_name")
    val playerName: String?,
    @ColumnInfo(name = "total_rounds")
    val totalRounds: Int,
    val score: Int? = null,
    val rounds: Map<PokemonEntity, String> = emptyMap(),
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "finished_at")
    val finishedAt: Long? = null
) {
    val finishedAtValue get() = finishedAt ?: 0L

    fun finishGameMatch(): GameMatchEntity = copy(finishedAt = System.currentTimeMillis())

    fun updateRound(updatedRounds: Map<PokemonEntity, String>): GameMatchEntity {
        return copy(rounds = updatedRounds)
    }
}
