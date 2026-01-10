package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.AdvanceRoundResult
import br.com.seucaio.pokeguess.domain.model.Pokemon

class AdvanceRoundUseCase(
    private val saveUserGuessUseCase: SaveUserGuessUseCase
) {
    suspend operator fun invoke(
        roundIndex: Int,
        totalRounds: Int,
        score: Int,
        pokemons: List<Pokemon>,
        pokemon: Pokemon?,
        guess: String
    ): Result<AdvanceRoundResult> {
        return runCatching {
            val isGameOver = pokemons.lastIndex == roundIndex

            saveUserGuessUseCase(
                score = score,
                guess = guess,
                pokemon = pokemon,
                isGameOver = isGameOver
            )

            if (isGameOver) {
                AdvanceRoundResult(nextRound = roundIndex, isGameOver = true, nextPokemon = null)
            } else {
                val nextRound = roundIndex + 1
                val nextPokemon = pokemons.getOrNull(nextRound)
                    ?: throw NoSuchElementException("No next Pokémon found")
                AdvanceRoundResult(
                    nextRound = nextRound,
                    isGameOver = false,
                    nextPokemon = nextPokemon
                )
            }
        }
    }
}
