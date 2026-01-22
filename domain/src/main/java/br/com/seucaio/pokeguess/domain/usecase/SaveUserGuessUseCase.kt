package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameRoundPlayer
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class SaveUserGuessUseCase(private val gameMatchRepository: GameMatchRepository) {
    suspend operator fun invoke(
        score: Int,
        guess: String,
        pokemon: Pokemon?,
        isGameOver: Boolean = false,
        player: String,
        roundPlayers: List<GameRoundPlayer>,
    ): Result<Unit> {
        return runCatching {
            gameMatchRepository.getLastMatch()?.let { gameMatch ->
                val updatedRoundsMultiplayer =
                    gameMatch.roundsMultiplayer.toMutableMap()
                gameMatch.roundsMultiplayer.keys.firstOrNull { pId -> pId == pokemon?.id }
                    ?.let { pokemonId ->
                        updatedRoundsMultiplayer.put(
                            key = pokemonId,
                            value = updatedRoundsMultiplayer[pokemonId]?.toMutableMap()?.apply {
                                put(player, guess)
                            } ?: emptyMap()
                        )
                    }

                val updatedRounds = gameMatch.rounds.toMutableMap()
                gameMatch.rounds.keys.firstOrNull { pId -> pId == pokemon?.id }
                    ?.let { pokemon -> updatedRounds.put(key = pokemon, value = guess) }

                val updatedMatch = gameMatch.copy(
                    score = score,
                    scorePlayers = gameMatch.scorePlayers.toMutableMap().apply {
                        put(player, score)
                    },
                    rounds = updatedRounds,
                    roundsMultiplayer = updatedRoundsMultiplayer
                )
                gameMatchRepository.updateMatch(match = updatedMatch, isGameOver = isGameOver)
            }
        }
    }
}
