package br.com.seucaio.pokeguess.data.repository

import br.com.seucaio.pokeguess.data.local.database.entity.PlayerEntity
import br.com.seucaio.pokeguess.data.local.source.PlayerLocalDataSource
import br.com.seucaio.pokeguess.domain.model.Player
import br.com.seucaio.pokeguess.domain.repository.PlayerRepository

class PlayerRepositoryImpl(private val localDataSource: PlayerLocalDataSource) : PlayerRepository {
    override suspend fun savePlayer(player: Player): Int {
        return localDataSource.insert(PlayerEntity(name = player.name)).toInt()
    }

    override suspend fun getPlayerById(playerId: Int): Player? {
        return localDataSource.getPlayerById(playerId)?.let {
            Player(id = it.id, name = it.name)
        }
    }

    override suspend fun getAllPlayers(): List<Player> {
        return localDataSource.getAllPlayers().map {
            Player(id = it.id, name = it.name)
        }
    }
}
