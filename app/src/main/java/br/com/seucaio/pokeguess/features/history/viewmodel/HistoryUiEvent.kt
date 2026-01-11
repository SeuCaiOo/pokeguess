package br.com.seucaio.pokeguess.features.history.viewmodel

sealed interface HistoryUiEvent {
    data class NavigateToScoreByMatchId(
        val matchId: Int,
        val score: Int,
        val total: Int,
        val withFriends: Boolean
    ) : HistoryUiEvent
}
