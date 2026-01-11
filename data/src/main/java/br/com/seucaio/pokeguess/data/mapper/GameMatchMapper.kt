package br.com.seucaio.pokeguess.data.mapper

import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchEntity
import br.com.seucaio.pokeguess.data.mapper.PokemonMapper.toPokemonDomain
import br.com.seucaio.pokeguess.data.mapper.PokemonMapper.toPokemonEntity
import br.com.seucaio.pokeguess.domain.model.GameMatch
import kotlin.collections.mapKeys

object GameMatchMapper {
    fun GameMatchEntity?.toDomain(): GameMatch {
        if (this == null) throw NoSuchElementException("GameMatch not found")
        return GameMatch(
            id = gameId,
            playerName = playerName,
            totalRounds = totalRounds,
            score = score,
            rounds = rounds,
        )
    }

    fun GameMatch.toEntity(): GameMatchEntity {
        return GameMatchEntity(
            gameId = id,
            playerName = playerName,
            totalRounds = totalRounds,
            score = score,
            rounds = rounds,
        )
    }
}
