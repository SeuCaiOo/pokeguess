package br.com.seucaio.pokeguess.data.mapper

import br.com.seucaio.pokeguess.data.local.database.entity.PlayerEntity
import br.com.seucaio.pokeguess.domain.model.Player

object PlayerMapper {
    fun PlayerEntity?.toDomain(): Player {
        if (this == null) throw NoSuchElementException("PlayerEntity not found")
        return Player(id = id, name = name)
    }

    fun List<PlayerEntity>.toDomainList(): List<Player> = map { it.toDomain() }


    fun Player.toEntity(): PlayerEntity {
        return PlayerEntity(id = id, name = name)
    }

    fun List<Player>.toEntityList(): List<PlayerEntity> = map { it.toEntity() }
}
