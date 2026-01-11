package br.com.seucaio.pokeguess.features.history.viewmodel

sealed interface HistoryUiAction {
    data object LoadHistory : HistoryUiAction
    data class MatchClicked(val matchId: Int) : HistoryUiAction
}
