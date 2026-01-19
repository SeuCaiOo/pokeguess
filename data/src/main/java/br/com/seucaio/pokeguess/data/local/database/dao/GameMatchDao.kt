package br.com.seucaio.pokeguess.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchEntity

@Dao
interface GameMatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matches: List<GameMatchEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: GameMatchEntity): Long

    @Update
    suspend fun update(match: GameMatchEntity)

    @Query(
        """ 
        UPDATE game_matches SET rounds = :rounds, score = :score, finished_at = :finishedAt 
        WHERE game_id = :gameId
        """
    )
    suspend fun updateRound(
        gameId: Int?,
        score: Int?,
        rounds: Map<Int, String>,
        finishedAt: Long?,
    )

    @Query("SELECT * FROM game_matches")
    suspend fun getAll(): List<GameMatchEntity>

    @Query("SELECT * FROM game_matches WHERE game_id = :gameId")
    suspend fun getMatchByGameId(gameId: Int): GameMatchEntity?

    @Query("SELECT * FROM game_matches WHERE finished_at IS NULL ORDER BY created_at DESC LIMIT 1")
    suspend fun getCurrentMatchActive(): GameMatchEntity?

    @Query("SELECT * FROM game_matches WHERE finished_at IS NOT NULL ORDER BY finished_at DESC LIMIT 1")
    suspend fun geLastFinishedGameMatch(): GameMatchEntity?

    @Query("DELETE FROM game_matches")
    suspend fun deleteAll()
}
