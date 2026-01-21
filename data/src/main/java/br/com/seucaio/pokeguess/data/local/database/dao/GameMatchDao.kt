package br.com.seucaio.pokeguess.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchEntity

@Dao
interface GameMatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matches: List<GameMatchEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: GameMatchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(match: GameMatchEntity)

    @Query(
        """ 
        UPDATE game_matchs SET rounds = :rounds, 
                rounds_multiplayer = :roundsMultiplayer, 
                score = :score, 
                score_players = :scorePlayers, 
                finished_at = :finishedAt    
        WHERE game_id = :gameId
        """
    )
    suspend fun updateRound(
        gameId: Int?,
        score: Int?,
        scorePlayers: Map<String, Int>,
        rounds: Map<Int, String>,
        roundsMultiplayer: Map<Int, Map<String, String>>,
        finishedAt: Long?,
    )

    @Query("SELECT * FROM game_matchs")
    suspend fun getAll(): List<GameMatchEntity>

    @Query("SELECT * FROM game_matchs WHERE game_id = :gameId")
    suspend fun getMatchByGameId(gameId: Int): GameMatchEntity?

    @Query("SELECT * FROM game_matchs WHERE finished_at IS NULL ORDER BY created_at DESC LIMIT 1")
    fun getCurrentMatchActive(): GameMatchEntity?

    @Query("SELECT * FROM game_matchs WHERE finished_at IS NOT NULL ORDER BY finished_at DESC LIMIT 1")
    suspend fun geLastFinishedGameMatch(): GameMatchEntity?

    @Query("DELETE FROM game_matchs")
    suspend fun deleteAll()
}
