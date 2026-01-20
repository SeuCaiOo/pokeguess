package br.com.seucaio.pokeguess.domain.repository

import br.com.seucaio.pokeguess.domain.model.Player

interface PlayerRepository {
    suspend fun savePlayers(players: List<Player>)
    suspend fun getPlayersByIds(playerIds: List<Int>): List<Player>
    suspend fun getPlayerByNames(playerNames: List<String>): List<Player>
    suspend fun getAllPlayers(): List<Player>
}
