package br.com.seucaio.pokeguess.features.score.viewmodel

import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.score.model.GameStatsUi

data class ScoreUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val gameStatsUi: GameStatsUi = GameStatsUi.default(),
    val withFriends: Boolean = false,
    val gameMatch: GameMatch? = null
) {
    val pokemons: Map<Pokemon, String>
        get() = gameMatch?.pokemonsWithGuesses.orEmpty()

    fun setLoading(isLoading: Boolean = true) = copy(isLoading = isLoading)

    fun setError(error: Throwable) = copy(errorMessage = error.message, isLoading = false)

    fun setGameStats(gameStatsUi: GameStatsUi) = copy(gameStatsUi = gameStatsUi, isLoading = false)

    fun setGameMatch(gameMatch: GameMatch) = copy(
        gameMatch = gameMatch,
        isLoading = false
    )
}
