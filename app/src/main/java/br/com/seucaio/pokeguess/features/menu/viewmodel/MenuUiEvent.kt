package br.com.seucaio.pokeguess.features.menu.viewmodel

sealed interface MenuUiEvent {
    data object NavigateToGame : MenuUiEvent
}
