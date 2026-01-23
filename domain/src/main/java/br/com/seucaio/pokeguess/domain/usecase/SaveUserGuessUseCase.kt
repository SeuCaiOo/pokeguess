package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameRoundPlayer
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class SaveUserGuessUseCase(private val gameMatchRepository: GameMatchRepository) {

    data class Params(
        val pokemon: Pokemon?,
        val isGameOver: Boolean = false,
        val roundPlayers: List<GameRoundPlayer>,
    )

    suspend operator fun invoke(params: Params): Result<Unit> {
        val pokemon = params.pokemon
        val isGameOver = params.isGameOver
        val roundPlayers = params.roundPlayers

        return runCatching {
            gameMatchRepository.getLastMatch()?.let { gameMatch ->
                val updatedRoundsMultiplayer =
                    gameMatch.roundsMultiplayer.toMutableMap()
                gameMatch.roundsMultiplayer.keys.firstOrNull { pId -> pId == pokemon?.id }
                    ?.let { pokemonId ->
                        updatedRoundsMultiplayer.put(
                            key = pokemonId,
                            value = updatedRoundsMultiplayer[pokemonId]?.toMutableMap()?.apply {
                                roundPlayers.forEach { roundPlayer ->
                                    put(roundPlayer.name, roundPlayer.guess)
                                }
                            } ?: emptyMap()
                        )
                    }

                val updatedRounds = gameMatch.rounds.toMutableMap()
                gameMatch.rounds.keys.firstOrNull { pId -> pId == pokemon?.id }
                    ?.let { pokemon ->
                        roundPlayers.forEach { roundPlayer ->
                            updatedRounds[pokemon] = roundPlayer.guess
                        }
                    }

                val updatedMatch = gameMatch.copy(
                    scorePlayers = gameMatch.scorePlayers.toMutableMap().apply {
                        roundPlayers.forEach { roundPlayer ->
                            put(roundPlayer.name, roundPlayer.score)
                        }
                    },
                    rounds = updatedRounds,
                    roundsMultiplayer = updatedRoundsMultiplayer
                )
                gameMatchRepository.updateMatch(match = updatedMatch, isGameOver = isGameOver)
            }
        }
    }
}
