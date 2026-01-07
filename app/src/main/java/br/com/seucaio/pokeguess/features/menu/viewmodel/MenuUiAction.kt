package br.com.seucaio.pokeguess.features.menu.viewmodel

import br.com.seucaio.pokeguess.domain.model.Generation

sealed interface MenuUiAction {
    data object StartGameClicked : MenuUiAction
    data class GenerationSelected(val generation: Generation) : MenuUiAction
    data class TimerToggled(val enabled: Boolean) : MenuUiAction
    data class NumberOfRoundsChanged(val rounds: Int) : MenuUiAction
}
