package br.com.seucaio.pokeguess.domain.repository

import br.com.seucaio.pokeguess.domain.model.GameRound

interface RoundRepository {
    suspend fun saveRound(gameId: Int, round: GameRound, pokemonId: Int, roundNumber: Int)
    suspend fun getRoundsByGameId(gameId: Int): List<GameRound>
}
