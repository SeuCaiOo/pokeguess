package br.com.seucaio.pokeguess.features.score.viewmodel

import br.com.seucaio.pokeguess.features.score.model.GameStatsUi

data class ScoreUiState(
    val gameStatsUi: GameStatsUi = GameStatsUi.default(),
    val withFriends: Boolean = false
)
