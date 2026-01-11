package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class SaveUserGuessUseCase(private val gameMatchRepository: GameMatchRepository) {
    suspend operator fun invoke(
        score: Int,
        guess: String,
        pokemon: Pokemon?,
        isGameOver: Boolean = false
    ): Result<Unit> {
        return runCatching {
            gameMatchRepository.getLastMatch()?.let { gameMatch ->
                val updatedRounds = gameMatch.rounds.toMutableMap()
                gameMatch.rounds.keys.firstOrNull { pId -> pId == pokemon?.id }
                    ?.let { pokemon -> updatedRounds.put(key = pokemon, value = guess) }

                val updatedMatch = gameMatch.copy(score = score, rounds = updatedRounds)
                gameMatchRepository.updateMatch(match = updatedMatch, isGameOver = isGameOver)
            }
        }
    }
}
