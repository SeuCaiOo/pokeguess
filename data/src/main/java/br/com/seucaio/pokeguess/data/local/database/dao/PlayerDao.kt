package br.com.seucaio.pokeguess.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.seucaio.pokeguess.data.local.database.entity.PlayerEntity

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(player: PlayerEntity)

    @Query("SELECT * FROM players WHERE id = :playerId")
    suspend fun getPlayerById(playerId: Int): PlayerEntity?

    @Query("SELECT * FROM players WHERE name = :playerName")
    suspend fun getPlayerByName(playerName: String): PlayerEntity?

    @Query("SELECT * FROM players WHERE id IN (:playerIds)")
    suspend fun getPlayersByIds(playerIds: List<Int>): List<PlayerEntity>

    @Query("SELECT * FROM players WHERE name IN (:playerNames)")
    suspend fun getPlayersByNames(playerNames: List<String>): List<PlayerEntity>

    @Query("SELECT * FROM players")
    suspend fun getAllPlayers(): List<PlayerEntity>
}
