package br.com.seucaio.pokeguess.features.game.viewmodel

import android.os.Parcelable
import br.com.seucaio.pokeguess.core.designsystem.ui.component.model.PokemonFrameData
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.game.model.GameUi
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pokemon: Pokemon? = null,
    val gameUi: GameUi = GameUi(),
    val withFriends: Boolean = false,
    val guessTyped: String = "",
) : Parcelable {
    val gameTimerEnabled get() = gameUi.isTimerEnabled
    val gameRemainingTime get() = gameUi.remainingTime

    fun toPokemonFrameData(): PokemonFrameData {
        return PokemonFrameData(
            pokemonName = pokemon?.name.orEmpty(),
            pokemonImageUrl = pokemon?.imageUrl.orEmpty(),
            unknownPokemon = !gameUi.guessSubmitted,
            pokemonType = "",
            guessCorrectly = gameUi.correctGuess
        )
    }

    fun setLoading(): GameUiState {
        return copy(isLoading = true, errorMessage = null)
    }

    fun setError(error: Throwable): GameUiState {
        return copy(isLoading = false, errorMessage = error.message ?: "Unknown error")
    }

    fun setSuccessPokemon(pokemon: Pokemon): GameUiState {
        return copy(isLoading = false, pokemon = pokemon)
    }

    fun checkGuess(guess: String, gameUi: GameUi): GameUiState {
        return copy(guessTyped = guess, gameUi = gameUi)
    }

    fun nextRound(gameUi: GameUi): GameUiState {
        return copy(guessTyped = "", gameUi = gameUi)
    }

    fun updateGameUi(gameUi: GameUi): GameUiState = copy(gameUi = gameUi)

    fun updateTimeGameUi(remainingTime: Int): GameUiState {
        return copy(gameUi = gameUi.updateTime(remainingTime))
    }

    fun updateGameUiState(update: GameUi.() -> GameUi): GameUiState {
        return copy(gameUi = gameUi.update())
    }
}
