package br.com.seucaio.pokeguess.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchPlayerEntity
import br.com.seucaio.pokeguess.data.local.database.entity.PlayerEntity

@Dao
interface GameMatchPlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameMatchPlayer: GameMatchPlayerEntity)

    @Query("""
        SELECT p.id, p.name, gmp.score FROM players p
        INNER JOIN game_match_players gmp ON p.id = gmp.player_id
        WHERE gmp.game_id = :gameId
    """)
    suspend fun getPlayersWithScoreForMatch(gameId: Int): List<PlayerWithScore>

    @Query("UPDATE game_match_players SET score = :score WHERE game_id = :gameId AND player_id = :playerId")
    suspend fun updatePlayerScore(gameId: Int, playerId: Int, score: Int)
}

data class PlayerWithScore(
    val id: Int,
    val name: String,
    val score: Int
)
