package br.com.seucaio.pokeguess.data.repository

import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchPlayerEntity
import br.com.seucaio.pokeguess.data.local.database.entity.RoundEntity
import br.com.seucaio.pokeguess.data.local.source.GameMatchLocalDataSource
import br.com.seucaio.pokeguess.data.local.source.GameMatchPlayerLocalDataSource
import br.com.seucaio.pokeguess.data.local.source.RoundLocalDataSource
import br.com.seucaio.pokeguess.data.mapper.GameMatchMapper.toDomain
import br.com.seucaio.pokeguess.data.mapper.GameMatchMapper.toEntity
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class GameMatchRepositoryImpl(
    private val matchLocalDataSource: GameMatchLocalDataSource,
    private val matchPlayerLocalDataSource: GameMatchPlayerLocalDataSource,
    private val roundLocalDataSource: RoundLocalDataSource
) : GameMatchRepository {

    override suspend fun getLastMatch(): GameMatch? {
        val gameMatchActive = matchLocalDataSource.getCurrentMatchActive()
        return gameMatchActive?.toDomain()
    }

    override suspend fun saveMatch(match: GameMatch): Int {
        val gameId = matchLocalDataSource.insert(match.toEntity()).toInt()

        match.players.forEach { player ->
            matchPlayerLocalDataSource.insert(
                GameMatchPlayerEntity(gameId = gameId, playerId = player.id, score = player.score)
            )
        }

        return gameId
    }

    override suspend fun updateMatch(match: GameMatch, isGameOver: Boolean) {
        match.toEntity().also {
            matchLocalDataSource.updateRound(
                gameId = it.gameId,
                score = it.score,
                rounds = it.rounds,
                finishedAt = if (isGameOver) System.currentTimeMillis() else null
            )
        }
    }

    override suspend fun getAllMatches(): List<GameMatch> {
        return matchLocalDataSource.getAll().map { it.toDomain() }
    }

    override suspend fun getMatchById(matchId: Int): GameMatch? {
        return matchLocalDataSource.getMatchByGameId(matchId)?.toDomain()
    }

    override suspend fun finishMatch(matchId: Int) {
        matchLocalDataSource.getMatchByGameId(matchId)?.let {
            matchLocalDataSource.update(it.copy(finishedAt = System.currentTimeMillis()))
        }
    }

    override suspend fun saveRound(
        gameId: Int,
        pokemonId: Int,
        roundNumber: Int,
        userGuess: String,
        isCorrect: Boolean
    ) {
        roundLocalDataSource.insert(
            RoundEntity(
                gameId = gameId,
                pokemonId = pokemonId,
                roundNumber = roundNumber,
                userGuess = userGuess,
                isCorrect = isCorrect
            )
        )
    }
}
