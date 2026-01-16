package br.com.seucaio.pokeguess.features.menu.viewmodel

import android.os.Parcelable
import br.com.seucaio.pokeguess.domain.model.GameSettings
import br.com.seucaio.pokeguess.domain.model.Generation
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuUiState(
    val generation: Generation = Generation.I,
    val timerEnabled: Boolean = false,
    val rounds: Int = 0,
    val withFriends: Boolean = false,
    val playerName: String = "",
) : Parcelable {
    val nameFilled get() = playerName.isNotBlank()
    val roundsFilled get() = rounds > 0
    val selectedGeneration get() = generation
    val startGameIsAvailable get() = nameFilled && roundsFilled

    fun setGeneration(generation: Generation): MenuUiState = copy(generation = generation)

    fun setTimer(enabled: Boolean): MenuUiState = copy(timerEnabled = enabled)

    fun setNumberRounds(rounds: Int): MenuUiState = copy(rounds = rounds)

    fun setName(name: String): MenuUiState = copy(playerName = name)

    fun setWithFriends(withFriends: Boolean): MenuUiState = copy(withFriends = withFriends)

    fun GameSettings.toMenuUiState(): MenuUiState {
        return MenuUiState(
            playerName = playerName,
            generation = generation,
            rounds = rounds,
            timerEnabled = timerEnabled,
            withFriends = withFriends
        )
    }

    companion object {
        fun MenuUiState.toGameSettings() = GameSettings(
            playerName = playerName,
            generation = generation,
            rounds = rounds,
            timerEnabled = timerEnabled,
            withFriends = withFriends
        )
    }
}
