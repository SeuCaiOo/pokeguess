package br.com.seucaio.pokeguess.features.home.viewmodel

sealed interface HomeUiEvent {
    data object NavigateToMenu : HomeUiEvent
    data object NavigateToHistory : HomeUiEvent
}
