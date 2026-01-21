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
    @ColumnInfo(name = "players")
    val players: List<String> = emptyList(),
    @ColumnInfo(name = "pokemons")
    val pokemonIds: Map<Int, String> = emptyMap(),
    @ColumnInfo(name = "pokemons_with_option")
    val pokemonsWithOption: Map<Int, List<String>> = emptyMap(),
    @ColumnInfo(name = "total_rounds")
    val totalRounds: Int,
    val score: Int? = null,
    @ColumnInfo(name = "score_players")
    val scorePlayers: Map<String, Int> = emptyMap(),
    val rounds: Map<Int, String> = emptyMap(),
    @ColumnInfo(name = "rounds_multiplayer")
    val roundsMultiplayer: Map<Int, Map<String, String>> = emptyMap(),
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "finished_at")
    val finishedAt: Long? = null
) {
    val finishedAtValue get() = finishedAt ?: 0L

    fun finishGameMatch(): GameMatchEntity = copy(finishedAt = System.currentTimeMillis())

    fun updateRound(updatedRounds: Map<Int, String>): GameMatchEntity {
        return copy(rounds = updatedRounds)
    }
}
