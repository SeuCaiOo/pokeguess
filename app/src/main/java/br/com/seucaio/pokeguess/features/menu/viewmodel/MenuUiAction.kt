package br.com.seucaio.pokeguess.features.menu.viewmodel

import br.com.seucaio.pokeguess.domain.model.Generation

sealed interface MenuUiAction {
    data object StartGameClicked : MenuUiAction
    data class GenerationSelected(val generation: Generation) : MenuUiAction
    data class TimerToggled(val enabled: Boolean) : MenuUiAction
    data class NumberOfRoundsChanged(val rounds: Int) : MenuUiAction
    data class PlayerNameChanged(val name: String, val index: Int = 0) : MenuUiAction
    data object PokemonListClicked : MenuUiAction
    data object BackButtonClicked : MenuUiAction
    data object AddNewPlayerClicked : MenuUiAction
    data class RemovePlayerClicked(val index: Int) : MenuUiAction
}
