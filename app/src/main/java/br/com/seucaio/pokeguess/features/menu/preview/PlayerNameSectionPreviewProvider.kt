package br.com.seucaio.pokeguess.features.menu.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState

class PlayerNameSectionPreviewProvider : PreviewParameterProvider<MenuUiState> {
    override val values: Sequence<MenuUiState> = sequenceOf(
        MenuUiState(),
        MenuUiState(
            playerNames = listOf("Player 1"),
        ),
        MenuUiState(
            playerNames = listOf(
                "Player 1",
                "Player 2",
            ),
        ),
        MenuUiState(
            playerNames = listOf(
                "Player 1",
                "Player 2",
                ""
            ),
        )
    )
}
