package br.com.seucaio.pokeguess.data.mapper

import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchEntity
import br.com.seucaio.pokeguess.domain.model.GameMatch

object GameMatchMapper {
    fun GameMatchEntity?.toDomain(): GameMatch {
        if (this == null) throw NoSuchElementException("GameMatch not found")
        return GameMatch(
            id = gameId,
            playerNames = playerNames,
            totalRounds = totalRounds,
            score = score,
            rounds = rounds,
            createdAt = createdAt,
            finishedAt = finishedAt
        )
    }

    fun GameMatch.toEntity(): GameMatchEntity {
        return GameMatchEntity(
            gameId = id,
            playerNames = playerNames,
            totalRounds = totalRounds,
            score = score,
            rounds = rounds,
            createdAt = createdAt,
            finishedAt = finishedAt
        )
    }
}
