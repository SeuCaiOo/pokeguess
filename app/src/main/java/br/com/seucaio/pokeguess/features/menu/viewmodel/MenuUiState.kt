package br.com.seucaio.pokeguess.features.menu.viewmodel

import android.os.Parcelable
import br.com.seucaio.pokeguess.domain.model.Difficulty
import br.com.seucaio.pokeguess.domain.model.GameSettings
import br.com.seucaio.pokeguess.domain.model.Generation
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuUiState(
    val generation: Generation = Generation.I,
    val difficulty: Difficulty = Difficulty.EASY,
    val timerEnabled: Boolean = false,
    val rounds: Int = 0,
    val playerNames: List<String> = listOf(""),
    val showPlayersBottomSheet: Boolean = playerNames.hasNoPlayers(),
) : Parcelable {
    val roundsLimit get() = HARD_ROUNDS
    val roundsFilled get() = rounds > 0
    val selectedGeneration get() = generation
    val multiPlayer: Boolean get() = playerNames.size > 1
    val confirmPlayers: Boolean
        get() = playerNames.all { it.isNotBlank() }
    val startGameIsAvailable: Boolean
        get() = playerNames.all { it.isNotBlank() } && roundsFilled

    fun setGeneration(generation: Generation): MenuUiState = copy(generation = generation)

    fun setDifficulty(difficulty: Difficulty): MenuUiState {
        val stateWithDifficulty = copy(difficulty = difficulty)
        return when (difficulty) {
            Difficulty.EASY -> {
                stateWithDifficulty
                    .setNumberRounds(EASY_ROUNDS)
                    .setTimer(false)
                    .setGeneration(Generation.I)
            }

            Difficulty.MEDIUM -> {
                stateWithDifficulty
                    .setNumberRounds(MEDIUM_ROUNDS)
                    .setTimer(true)
                    .setGeneration(Generation.ALL)
            }

            Difficulty.HARD -> {
                stateWithDifficulty
                    .setNumberRounds(HARD_ROUNDS)
                    .setTimer(true)
                    .setGeneration(Generation.ALL)
            }
        }
    }

    fun setTimer(enabled: Boolean): MenuUiState = copy(timerEnabled = enabled)

    fun setNumberRounds(rounds: Int): MenuUiState = copy(rounds = rounds)

    fun addPlayer(): MenuUiState {
        val newPlayers = playerNames.toMutableList().also { it.add("") }.toList()
        return copy(playerNames = newPlayers)
    }

    fun setPlayer(name: String, index: Int = 0): MenuUiState {
        val newPlayers = playerNames.toMutableList().also {
            it.getOrNull(index)?.let { _ -> it[index] = name } ?: it.add(name)
        }.toList()
        return copy(playerNames = newPlayers)
    }

    fun removePlayer(index: Int): MenuUiState {
        val newPlayers = playerNames.toMutableList().also { it.removeAt(index) }.toList()
        return copy(playerNames = newPlayers)
    }

    fun setPlayersBottomSheetVisibility(visible: Boolean): MenuUiState =
        copy(showPlayersBottomSheet = visible)

    fun GameSettings.toMenuUiState(): MenuUiState {
        val players = playerNames.ifEmpty { listOf("") }
        return MenuUiState(
            playerNames = players,
            generation = generation,
            difficulty = difficulty,
            rounds = rounds,
            timerEnabled = timerEnabled
        )
    }

    companion object {
        private const val EASY_ROUNDS = 5
        private const val MEDIUM_ROUNDS = 10
        private const val HARD_ROUNDS = 20

        fun MenuUiState.toGameSettings() = GameSettings(
            playerNames = playerNames,
            generation = generation,
            difficulty = difficulty,
            rounds = rounds,
            timerEnabled = timerEnabled,
        )

        private fun List<String>.hasNoPlayers() = all { it.isBlank() }
    }
}
