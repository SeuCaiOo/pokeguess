package br.com.seucaio.pokeguess.domain.usecase

class ValidateGuessUseCase {
    operator fun invoke(guess: String, pokemonName: String): Boolean {
        return guess.trim().equals(pokemonName, ignoreCase = true)
    }
}
