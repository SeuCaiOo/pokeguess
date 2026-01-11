package br.com.seucaio.pokeguess.features.history.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.AccuracyLevel
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.features.history.viewmodel.HistoryUiState
import br.com.seucaio.pokeguess.features.score.model.GameStatsUi
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiState

class HistoryUiStatePreviewProvider : PreviewParameterProvider<HistoryUiState> {
    override val values: Sequence<HistoryUiState> = sequenceOf(
        HistoryUiState(
            isLoading = true,
            matches = listOf()
        ),
        HistoryUiState(
            isLoading = false,
            matches = emptyList()
        ),
        HistoryUiState(
            isLoading = false,
            matches = listOf(
                GameMatch(
                    id = 1,
                    playerName = "Player 1",
                    score = 10,
                    totalRounds = 10,
                    createdAt = System.currentTimeMillis(),
                    finishedAt = System.currentTimeMillis()
                ),
                GameMatch(
                    id = 2,
                    playerName = "Player 2",
                    score = 8,
                    totalRounds = 10
                ),
                GameMatch(
                    id = 3,
                    playerName = "Player 3",
                    score = 6,
                    totalRounds = 10
                )
            )
        )
    )
}
