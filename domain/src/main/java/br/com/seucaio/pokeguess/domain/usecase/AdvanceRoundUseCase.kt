package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.AdvanceRoundResult
import br.com.seucaio.pokeguess.domain.model.GameRoundPlayer
import br.com.seucaio.pokeguess.domain.model.Pokemon

class AdvanceRoundUseCase(
    private val saveUserGuessUseCase: SaveUserGuessUseCase
) {
    data class Params(
        val roundIndex: Int,
        val totalRounds: Int,
        val score: Int,
        val pokemonMatchs: List<Pokemon>,
        val currentPokemon: Pokemon?,
        val guessTyped: String,
        val roundPlayers: List<GameRoundPlayer>,
    )

    suspend operator fun invoke(params: Params): Result<AdvanceRoundResult> {
        return runCatching {
            val isGameOver = params.pokemonMatchs.lastIndex == params.roundIndex

            saveUserGuessUseCase(
                score = params.score,
                guess = params.guessTyped,
                pokemon = params.currentPokemon,
                isGameOver = isGameOver,
                player = params.roundPlayers.first().name,
                roundPlayers = params.roundPlayers
            )

            if (isGameOver) {
                AdvanceRoundResult(
                    nextRound = params.roundIndex,
                    isGameOver = true,
                    nextPokemon = null
                )
            } else {
                val nextRound = params.roundIndex + 1
                val nextPokemon = params.pokemonMatchs.getOrNull(nextRound)
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
