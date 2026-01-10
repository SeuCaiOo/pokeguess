package br.com.seucaio.pokeguess.domain.repository

import br.com.seucaio.pokeguess.domain.model.GameMatch

interface GameMatchRepository {
    suspend fun getLastMatch(): GameMatch?
    suspend fun saveMatch(match: GameMatch)
    suspend fun updateMatch(match: GameMatch, isGameOver: Boolean)
}
