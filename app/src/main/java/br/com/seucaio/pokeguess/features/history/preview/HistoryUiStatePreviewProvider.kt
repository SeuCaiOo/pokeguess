package br.com.seucaio.pokeguess.features.history.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.features.history.viewmodel.HistoryUiState

class HistoryUiStatePreviewProvider : PreviewParameterProvider<HistoryUiState> {
    override val values: Sequence<HistoryUiState> = sequenceOf(
        HistoryUiState(
            isLoading = false,
            matches = listOf(
                GameMatch(
                    id = 1,
                    playerNames = listOf("Player 1"),
                    score = 10,
                    totalRounds = 10,
                    createdAt = System.currentTimeMillis(),
                    finishedAt = System.currentTimeMillis()
                ),
                GameMatch(
                    id = 2,
                    playerNames = listOf("Player 1", "Player 2"),
                    score = 8,
                    totalRounds = 10
                ),
                GameMatch(
                    id = 3,
                    playerNames = listOf("Player 3"),
                    score = 6,
                    totalRounds = 10
                )
            )
        ),
        HistoryUiState(
            isLoading = true,
            matches = listOf()
        ),
        HistoryUiState(
            isLoading = false,
            matches = emptyList()
        )
    )
}
