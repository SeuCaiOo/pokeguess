package br.com.seucaio.pokeguess.data.local.source

import br.com.seucaio.pokeguess.data.local.database.dao.PlayerDao
import br.com.seucaio.pokeguess.data.local.database.entity.PlayerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface PlayerLocalDataSource {
    suspend fun insert(player: PlayerEntity): Long
    suspend fun getPlayerById(playerId: Int): PlayerEntity?
    suspend fun getAllPlayers(): List<PlayerEntity>
}

class PlayerLocalDataSourceImpl(
    private val playerDao: PlayerDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PlayerLocalDataSource {
    override suspend fun insert(player: PlayerEntity): Long {
        return withContext(ioDispatcher) { playerDao.insert(player) }
    }

    override suspend fun getPlayerById(playerId: Int): PlayerEntity? {
        return withContext(ioDispatcher) { playerDao.getPlayerById(playerId) }
    }

    override suspend fun getAllPlayers(): List<PlayerEntity> {
        return withContext(ioDispatcher) { playerDao.getAllPlayers() }
    }
}
