package br.com.seucaio.pokeguess.features.score.viewmodel

sealed interface ScoreUiAction {
    data object PlayAgainClicked : ScoreUiAction
    data object BackToHomeClicked : ScoreUiAction
}
