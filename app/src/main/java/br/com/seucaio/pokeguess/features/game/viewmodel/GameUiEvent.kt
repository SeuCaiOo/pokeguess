package br.com.seucaio.pokeguess.features.game.viewmodel

sealed interface GameUiEvent {
    data class GameOver(
        val score: Int,
        val total: Int,
        val withFriends: Boolean
    ) : GameUiEvent
    data object NavigateBack : GameUiEvent
}
