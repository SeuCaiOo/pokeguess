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
    val players: List<String> = listOf(""),
    val showPlayersBottomSheet: Boolean = false,
) : Parcelable {
    val roundsFilled get() = rounds > 0
    val selectedGeneration get() = generation
    val multiPlayer: Boolean get() = players.size > 1
    val confirmPlayers: Boolean
        get() {
            return if (multiPlayer) players.all { it.isNotBlank() } else false
        }
    val startGameIsAvailable: Boolean
        get() {
            return if (multiPlayer) players.all { it.isNotBlank() } && roundsFilled else false
        }

    fun setGeneration(generation: Generation): MenuUiState = copy(generation = generation)

    fun setTimer(enabled: Boolean): MenuUiState = copy(timerEnabled = enabled)

    fun setNumberRounds(rounds: Int): MenuUiState = copy(rounds = rounds)

    fun addPlayer(): MenuUiState {
        val newPlayers = players.toMutableList().also { it.add("") }.toList()
        return copy(players = newPlayers)
    }

    fun setPlayer(name: String, index: Int = 0): MenuUiState {
        val newPlayers = players.toMutableList().also {
            it.getOrNull(index)?.let { _ -> it[index] = name } ?: it.add(name)
        }.toList()
        return copy(players = newPlayers)
    }

    fun removePlayer(index: Int): MenuUiState {
        val newPlayers = players.toMutableList().also { it.removeAt(index) }.toList()
        return copy(players = newPlayers)
    }

    fun setPlayersBottomSheetVisibility(visible: Boolean): MenuUiState =
        copy(showPlayersBottomSheet = visible)

    fun GameSettings.toMenuUiState(): MenuUiState {
        return MenuUiState(
            players = players,
            generation = generation,
            rounds = rounds,
            timerEnabled = timerEnabled
        )
    }

    companion object {
        fun MenuUiState.toGameSettings() = GameSettings(
            players = players,
            generation = generation,
            rounds = rounds,
            timerEnabled = timerEnabled,
        )
    }
}
