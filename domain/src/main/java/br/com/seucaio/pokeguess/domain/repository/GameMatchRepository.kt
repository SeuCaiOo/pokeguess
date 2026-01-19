package br.com.seucaio.pokeguess.domain.repository

import br.com.seucaio.pokeguess.domain.model.GameMatch

interface GameMatchRepository {
    suspend fun getLastMatch(): GameMatch?
    suspend fun saveMatch(match: GameMatch): Int
    suspend fun updateMatch(match: GameMatch, isGameOver: Boolean)
    suspend fun getAllMatches(): List<GameMatch>
    suspend fun getMatchById(matchId: Int): GameMatch?
    suspend fun finishMatch(matchId: Int)
    suspend fun saveRound(gameId: Int, pokemonId: Int, roundNumber: Int, userGuess: String, isCorrect: Boolean)
}
