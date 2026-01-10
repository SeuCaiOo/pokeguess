package br.com.seucaio.pokeguess.data.local.source

import br.com.seucaio.pokeguess.data.local.database.dao.GameMatchDao
import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchEntity
import br.com.seucaio.pokeguess.data.local.database.entity.PokemonEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GameMatchLocalDataSource {
    suspend fun insertAll(matches: List<GameMatchEntity>)
    suspend fun insert(match: GameMatchEntity)
    suspend fun update(match: GameMatchEntity)
    suspend fun updateRound(
        gameId: Int?,
        score: Int?,
        rounds: Map<PokemonEntity, String>,
        finishedAt: Long?
    )

    suspend fun getAll(): List<GameMatchEntity>
    suspend fun getMatchByGameId(gameId: Int): GameMatchEntity
    suspend fun getMatchListByPlayerName(playerName: String): List<GameMatchEntity>
    suspend fun getLastFinishedGameMatch(): GameMatchEntity?
    suspend fun getCurrentMatchActive(): GameMatchEntity?
    suspend fun deleteAll()
}

class GameMatchLocalDataSourceImpl(
    private val gameMatchDao: GameMatchDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GameMatchLocalDataSource {
    override suspend fun insertAll(matches: List<GameMatchEntity>) {
        return withContext(ioDispatcher) { gameMatchDao.insertAll(matches) }
    }

    override suspend fun insert(match: GameMatchEntity) {
        return withContext(ioDispatcher) { gameMatchDao.insert(match) }
    }

    override suspend fun update(match: GameMatchEntity) {
        return withContext(ioDispatcher) { gameMatchDao.update(match) }
    }

    override suspend fun updateRound(
        gameId: Int?,
        score: Int?,
        rounds: Map<PokemonEntity, String>,
        finishedAt: Long?
    ) {
        return withContext(ioDispatcher) {
            gameMatchDao.updateRound(
                gameId = gameId,
                score = score,
                rounds = rounds,
                finishedAt = finishedAt
            )
        }
    }

    override suspend fun getAll(): List<GameMatchEntity> {
        return withContext(ioDispatcher) { gameMatchDao.getAll() }
    }

    override suspend fun getMatchByGameId(gameId: Int): GameMatchEntity {
        return withContext(ioDispatcher) { gameMatchDao.getMatchByGameId(gameId) }
    }

    override suspend fun getMatchListByPlayerName(playerName: String): List<GameMatchEntity> {
        return withContext(ioDispatcher) { gameMatchDao.getMatchListByPlayerName(playerName) }
    }

    override suspend fun getLastFinishedGameMatch(): GameMatchEntity? {
        return withContext(ioDispatcher) { gameMatchDao.geLastFinishedGameMatch() }
    }

    override suspend fun getCurrentMatchActive(): GameMatchEntity? {
        return withContext(ioDispatcher) { gameMatchDao.getCurrentMatchActive() }
    }

    override suspend fun deleteAll() {
        return withContext(ioDispatcher) { gameMatchDao.deleteAll() }
    }
}
