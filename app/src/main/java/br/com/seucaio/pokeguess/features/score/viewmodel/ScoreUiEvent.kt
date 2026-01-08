package br.com.seucaio.pokeguess.features.score.viewmodel

sealed interface ScoreUiEvent {
    data object NavigateToHome : ScoreUiEvent
    data object NavigateToMenu : ScoreUiEvent
}
