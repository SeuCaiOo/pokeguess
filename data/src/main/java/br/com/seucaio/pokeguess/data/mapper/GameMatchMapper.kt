package br.com.seucaio.pokeguess.data.mapper

import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchEntity
import br.com.seucaio.pokeguess.domain.model.GameMatch

object GameMatchMapper {
    fun GameMatchEntity?.toDomain(): GameMatch {
        if (this == null) throw NoSuchElementException("GameMatch not found")
        return GameMatch(
            id = gameId,
            players = players,
            pokemonIds = pokemonIds,
            pokemonsWithOption = pokemonsWithOption,
            totalRounds = totalRounds,
            score = score,
            rounds = rounds,
            roundsMultiplayer = roundsMultiplayer,
            createdAt = createdAt,
            finishedAt = finishedAt
        )
    }

    fun GameMatch.toEntity(): GameMatchEntity {
        return GameMatchEntity(
            gameId = id,
            players = players,
            pokemonIds = pokemons.map { it.id },
            pokemonsWithOption = pokemonsWithOption,
            totalRounds = totalRounds,
            score = score,
            rounds = rounds,
            roundsMultiplayer = roundsMultiplayer,
            createdAt = createdAt,
            finishedAt = finishedAt
        )
    }
}
