package br.com.seucaio.pokeguess.features.home.viewmodel

sealed interface HomeUiEvent {
    data object NavigateToSoloMode : HomeUiEvent
    data object NavigateToFriendsMode : HomeUiEvent
    data object NavigateToHistory : HomeUiEvent
}
