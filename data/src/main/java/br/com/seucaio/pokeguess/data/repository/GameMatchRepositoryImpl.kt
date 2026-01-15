package br.com.seucaio.pokeguess.data.repository

import br.com.seucaio.pokeguess.data.local.source.GameMatchLocalDataSource
import br.com.seucaio.pokeguess.data.mapper.GameMatchMapper.toDomain
import br.com.seucaio.pokeguess.data.mapper.GameMatchMapper.toEntity
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class GameMatchRepositoryImpl(private val localDataSource: GameMatchLocalDataSource) :
    GameMatchRepository {
    override suspend fun getLastMatch(): GameMatch? {
        val gameMatchActive = localDataSource.getCurrentMatchActive()
        return gameMatchActive?.toDomain()
    }

    override suspend fun saveMatch(match: GameMatch) {
        localDataSource.insert(match.toEntity())
    }

    override suspend fun updateMatch(match: GameMatch, isGameOver: Boolean) {
        match.toEntity().also {
            localDataSource.updateRound(
                gameId = it.gameId,
                score = it.score,
                rounds = it.rounds,
                finishedAt = if (isGameOver) System.currentTimeMillis() else null
            )
        }
    }

    override suspend fun getAllMatches(): List<GameMatch> {
        return localDataSource.getAll().map { it.toDomain() }
    }

    override suspend fun getMatchById(matchId: Int): GameMatch? {
        return localDataSource.getMatchByGameId(matchId)?.toDomain()
    }
}
