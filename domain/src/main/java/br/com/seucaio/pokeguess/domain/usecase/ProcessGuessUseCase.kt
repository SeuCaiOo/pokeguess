package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GuessResult

class ProcessGuessUseCase(private val validateGuessUseCase: ValidateGuessUseCase) {
    operator fun invoke(guess: String, pokemonName: String, currentScore: Int): GuessResult {
        val isCorrect = validateGuessUseCase(guess, pokemonName)
        val newScore = if (isCorrect) currentScore + 1 else currentScore
        return GuessResult(isCorrect, newScore)
    }
}

