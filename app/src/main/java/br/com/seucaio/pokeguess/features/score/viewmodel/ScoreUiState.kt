package br.com.seucaio.pokeguess.features.score.viewmodel

import android.os.Parcelable
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.GameStats
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.score.model.GameStatsUi
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScoreUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val withFriends: Boolean = false,
    val gameMatch: GameMatch? = null,
    val playerStats: List<GameStatsUi> = emptyList()
) : Parcelable {
    val pokemonsWithGuesses: Map<Pokemon, String>
        get() = gameMatch?.pokemonsWithGuesses.orEmpty()

    val pokemons: List<Pokemon>
        get() = gameMatch?.pokemons.orEmpty()

    val roundPlayers: Map<Int, Map<String, String>>
        get() = gameMatch?.roundsMultiplayer.orEmpty()

    val scorePlayers: Map<String, Int>
        get() = gameMatch?.scorePlayers.orEmpty()

    fun setLoading(isLoading: Boolean = true) = copy(isLoading = isLoading)

    fun setError(error: Throwable) = copy(errorMessage = error.message, isLoading = false)

    fun setGameMatch(gameMatch: GameMatch, playerStats: List<GameStats>) = copy(
        gameMatch = gameMatch,
        playerStats = playerStats.map { GameStatsUi.fromGameStats(it) },
        isLoading = false
    )
}
