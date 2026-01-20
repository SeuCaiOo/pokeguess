package br.com.seucaio.pokeguess.data.local.source

import br.com.seucaio.pokeguess.data.local.database.dao.PlayerDao
import br.com.seucaio.pokeguess.data.local.database.entity.PlayerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface PlayerLocalDataSource {
    suspend fun insertPlayers(players: List<PlayerEntity>)
    suspend fun getPlayersByIds(playerIds: List<Int>): List<PlayerEntity>
    suspend fun getPlayerByNames(playerNames: List<String>): List<PlayerEntity>
    suspend fun getAllPlayers(): List<PlayerEntity>
}

class PlayerLocalDataSourceImpl(
    private val playerDao: PlayerDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PlayerLocalDataSource {

    override suspend fun insertPlayers(players: List<PlayerEntity>) {
        withContext(ioDispatcher) {
            val playerNames = players.map { it.name }
            playerDao.getPlayersByNames(playerNames)
                .filter { playerEntity -> playerEntity.name !in playerNames }
                .also { playerDao.insertAll(it) }
        }
    }

    override suspend fun getPlayersByIds(playerIds: List<Int>): List<PlayerEntity> {
        return withContext(ioDispatcher) { playerDao.getPlayersByIds(playerIds) }
    }

    override suspend fun getPlayerByNames(playerNames: List<String>): List<PlayerEntity> {
        return withContext(ioDispatcher) { playerDao.getPlayersByNames(playerNames) }
    }

    override suspend fun getAllPlayers(): List<PlayerEntity> {
        return withContext(ioDispatcher) { playerDao.getAllPlayers() }
    }
}
