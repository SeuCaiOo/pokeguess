package br.com.seucaio.pokeguess.features.game.viewmodel

import android.os.Parcelable
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.common.extension.orZero
import br.com.seucaio.pokeguess.core.designsystem.ui.component.model.PokemonFrameData
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.game.model.GameUi
import br.com.seucaio.pokeguess.features.game.model.RoundPlayerUi
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pokemon: Pokemon? = null,
    val pokemonMatchs: List<Pokemon> = emptyList(),
    val gameUi: GameUi = GameUi(),
    val withFriends: Boolean = false,
    val guessTyped: String = "",
    val showGuessBottomSheet: Boolean = false,
    val skipGuess: Boolean = false,
    val gamemMatch: GameMatch? = null,
    val roundPlayers: List<RoundPlayerUi> = emptyList()
) : Parcelable {
    val gameTimerEnabled get() = gameUi.isTimerEnabled
    val gameRemainingTime get() = gameUi.remainingTime

    val selectedPlayer get() = roundPlayers.firstOrNull { it.selected }?.name.orEmpty()

    val roundPlayerSelected: RoundPlayerUi get() = roundPlayers.single { it.selected }

    val multiplayerGame get() = gamemMatch?.players?.size.orZero() > 1
    val guessFilled: Boolean
        get() {
            return roundPlayers.isNotEmpty() && roundPlayers.all { it.filledGuess }
        }

    val buttonConfirmRes get() = if (guessFilled) R.string.confirm else R.string.skip

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
        return copy(isLoading = false, errorMessage = error.message)
    }

    fun setMatchsPokemon(pokemonMatchs: List<Pokemon>): GameUiState {
        return copy(
            isLoading = false,
            pokemonMatchs = pokemonMatchs,
            pokemon = pokemonMatchs.first(),
        )
    }

    fun setGuess(guess: String): GameUiState {
        val newRoundPlayers = roundPlayers.toMutableList()
        val index = newRoundPlayers.indexOfFirst { it.selected }
        newRoundPlayers[index] = newRoundPlayers[index].changeGuess(guess)
        return copy(
            guessTyped = guess,
            roundPlayers = newRoundPlayers
        )
    }

    fun checkGuess(guess: String, gameUi: GameUi, pokemonName: String): GameUiState {
        val newRoundPlayers = roundPlayers.toMutableList()

        return copy(
            guessTyped = guess,
            gameUi = gameUi,
            skipGuess = guess.isBlank(),
            showGuessBottomSheet = false,
            roundPlayers = newRoundPlayers.map { it.checkGuess(pokemonName) }
        )
    }

    fun skipGuess(): GameUiState {
        val newRoundPlayers = roundPlayers.toMutableList()
        val index = newRoundPlayers.indexOfFirst { it.selected }
        newRoundPlayers[index] = newRoundPlayers[index].setSkipGuess()
        return copy(skipGuess = true, showGuessBottomSheet = false, roundPlayers = newRoundPlayers)
    }

    fun fillGuessPlayer(guess: String): GameUiState {
        val newRoundPlayers = roundPlayers.toMutableList()
        val index = newRoundPlayers.indexOfFirst { it.selected }
        newRoundPlayers[index] = newRoundPlayers[index].setGuess(guess).unselect()
        return copy(
            roundPlayers = newRoundPlayers,
            showGuessBottomSheet = false,
            guessTyped = "",
        )
    }

    fun nextRound(gameUi: GameUi, nextPokemon: Pokemon?): GameUiState {
        return copy(
            guessTyped = "",
            skipGuess = false,
            gameUi = gameUi,
            pokemon = nextPokemon,
            roundPlayers = roundPlayers.map { it.resetRound() }
        )
    }

    fun updateGameUi(gameUi: GameUi): GameUiState = copy(gameUi = gameUi)

    fun setGuessBottomSheetVisibility(
        visible: Boolean,
        index: Int?
    ): GameUiState {
        val newRoundPlayers = roundPlayers.toMutableList()
        var resetTextField = false
        index?.let { i ->
            if (visible) {
                roundPlayers.getOrNull(index)?.select()?.let { newRoundPlayers[i] = it }
            } else {
                roundPlayers.getOrNull(index)?.unselect()?.let { newRoundPlayers[i] = it }
            }
        } ?: run {
            roundPlayers.firstOrNull { it.selected }?.let {
                newRoundPlayers[roundPlayers.indexOf(it)] = it.unselect().changeGuess("")
                resetTextField = true
            }
        }

        return copy(
            showGuessBottomSheet = visible,
            roundPlayers = newRoundPlayers,
            guessTyped = if (resetTextField) "" else guessTyped
        )
    }

    fun setGameMatch(gameMatch: GameMatch): GameUiState {
        return copy(
            gamemMatch = gameMatch,
            roundPlayers = gameMatch.players.map { RoundPlayerUi(name = it) },
        ).setMatchsPokemon(pokemonMatchs = gameMatch.pokemons)
    }
}
