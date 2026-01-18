package br.com.seucaio.pokeguess.features.game.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.features.game.model.GameUi
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

class GameHeaderSectionPreviewProvider : PreviewParameterProvider<GameUiState> {
    override val values: Sequence<GameUiState> = sequenceOf(
        GameUiState(),
        GameUiState(
            gameUi = GameUi(
                remainingTime = 7,
                isTimerEnabled = true,
                totalRounds = 10,
                score = 10,
            )
        ),
        GameUiState(
            gameUi = GameUi(
                remainingTime = 0,
                isTimerEnabled = true,
                totalRounds = 10
            )
        )
    )
}
