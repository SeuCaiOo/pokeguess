package br.com.seucaio.pokeguess.features.menu.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState

class SettingsSectionPreviewProvider : PreviewParameterProvider<MenuUiState> {
    override val values: Sequence<MenuUiState> = sequenceOf(
        MenuUiState(),
        MenuUiState(
            playerNames = listOf(
                "Player 1",
            ),
            timerEnabled = true,
        ),
        MenuUiState(
            playerNames = listOf(""),
        ),
        MenuUiState(
            playerNames = listOf(
                "Player 1",
                "Player 2",
                ""
            ),
            rounds = 5,
        )
    )
}
