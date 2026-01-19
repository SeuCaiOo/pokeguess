package br.com.seucaio.pokeguess.data.local.source

import br.com.seucaio.pokeguess.data.local.database.dao.RoundDao
import br.com.seucaio.pokeguess.data.local.database.dao.RoundWithPokemon
import br.com.seucaio.pokeguess.data.local.database.entity.RoundEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RoundLocalDataSource {
    suspend fun insert(round: RoundEntity): Long
    suspend fun getRoundsByGameId(gameId: Int): List<RoundWithPokemon>
}

class RoundLocalDataSourceImpl(
    private val roundDao: RoundDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RoundLocalDataSource {
    override suspend fun insert(round: RoundEntity): Long {
        return withContext(ioDispatcher) { roundDao.insert(round) }
    }

    override suspend fun getRoundsByGameId(gameId: Int): List<RoundWithPokemon> {
        return withContext(ioDispatcher) { roundDao.getRoundsWithPokemonByGameId(gameId) }
    }
}
