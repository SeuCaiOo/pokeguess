package br.com.seucaio.pokeguess.data.local.source

import br.com.seucaio.pokeguess.data.local.database.dao.GameMatchPlayerDao
import br.com.seucaio.pokeguess.data.local.database.dao.PlayerWithScore
import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchPlayerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GameMatchPlayerLocalDataSource {
    suspend fun insert(gameMatchPlayer: GameMatchPlayerEntity)
    suspend fun getPlayersWithScoreForMatch(gameId: Int): List<PlayerWithScore>
    suspend fun updatePlayerScore(gameId: Int, playerId: Int, score: Int)
}

class GameMatchPlayerLocalDataSourceImpl(
    private val gameMatchPlayerDao: GameMatchPlayerDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GameMatchPlayerLocalDataSource {
    override suspend fun insert(gameMatchPlayer: GameMatchPlayerEntity) {
        withContext(ioDispatcher) { gameMatchPlayerDao.insert(gameMatchPlayer) }
    }

    override suspend fun getPlayersWithScoreForMatch(gameId: Int): List<PlayerWithScore> {
        return withContext(ioDispatcher) { gameMatchPlayerDao.getPlayersWithScoreForMatch(gameId) }
    }

    override suspend fun updatePlayerScore(gameId: Int, playerId: Int, score: Int) {
        withContext(ioDispatcher) { gameMatchPlayerDao.updatePlayerScore(gameId, playerId, score) }
    }
}
