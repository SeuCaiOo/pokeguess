package br.com.seucaio.pokeguess.features.history.viewmodel

sealed interface HistoryUiEvent {
    data class NavigateToScoreByMatchId(val matchId: Int) : HistoryUiEvent
}
