package br.com.seucaio.pokeguess.features.menu.viewmodel

import br.com.seucaio.pokeguess.domain.model.Generation

data class MenuUiState(
    val selectedGeneration: Generation = Generation.I,
    val timerEnabled: Boolean = false,
    val rounds: Int = 10,
    val withFriends: Boolean = false,
) {
    fun setGeneration(generation: Generation): MenuUiState = copy(selectedGeneration = generation)

    fun setTimer(enabled: Boolean): MenuUiState = copy(timerEnabled = enabled)

    fun setNumberRounds(rounds: Int): MenuUiState = copy(rounds = rounds)
}
