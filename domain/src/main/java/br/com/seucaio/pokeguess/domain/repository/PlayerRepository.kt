package br.com.seucaio.pokeguess.domain.repository

import br.com.seucaio.pokeguess.domain.model.Player

interface PlayerRepository {
    suspend fun savePlayer(player: Player): Int
    suspend fun getPlayerById(playerId: Int): Player?
    suspend fun getAllPlayers(): List<Player>
}
