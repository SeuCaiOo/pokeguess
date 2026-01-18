package br.com.seucaio.pokeguess.features.score.viewmodel

sealed interface ScoreUiEvent {
    data object NavigateToHome : ScoreUiEvent
    data class NavigateToMenu(val withFriends: Boolean) : ScoreUiEvent
    data object NavigateToBack : ScoreUiEvent
}
