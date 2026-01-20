package br.com.seucaio.pokeguess.data.repository

import br.com.seucaio.pokeguess.data.local.source.PlayerLocalDataSource
import br.com.seucaio.pokeguess.data.mapper.PlayerMapper.toDomainList
import br.com.seucaio.pokeguess.data.mapper.PlayerMapper.toEntityList
import br.com.seucaio.pokeguess.data.mapper.PlayerMapper.toPlayerFromNames
import br.com.seucaio.pokeguess.domain.model.Player
import br.com.seucaio.pokeguess.domain.repository.PlayerRepository

class PlayerRepositoryImpl(private val localDataSource: PlayerLocalDataSource) : PlayerRepository {
    override suspend fun savePlayers(players: List<Player>) {
        localDataSource.insertPlayers(players.toEntityList())
    }

    override suspend fun savePlayerByNames(playerNames: List<String>) {
        localDataSource.insertPlayers(playerNames.toPlayerFromNames().toEntityList())
    }

    override suspend fun getPlayersByIds(playerIds: List<Int>): List<Player> {
        return localDataSource.getPlayersByIds(playerIds).toDomainList()
    }

    override suspend fun getPlayerByNames(playerNames: List<String>): List<Player> {
        return localDataSource.getPlayerByNames(playerNames).toDomainList()
    }

    override suspend fun getAllPlayers(): List<Player> {
        return localDataSource.getAllPlayers().toDomainList()
    }
}
