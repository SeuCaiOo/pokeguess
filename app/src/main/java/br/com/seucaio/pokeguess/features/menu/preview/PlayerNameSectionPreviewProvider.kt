package br.com.seucaio.pokeguess.features.menu.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState

class PlayerNameSectionPreviewProvider : PreviewParameterProvider<MenuUiState> {
    override val values: Sequence<MenuUiState> = sequenceOf(
        MenuUiState(),
        MenuUiState(
            players = listOf(""),
            withFriends = true
        ),
        MenuUiState(
            players = listOf("Player 1"),
        ),
        MenuUiState(
            players = listOf(
                "Player 1",
            ),
            withFriends = true
        ),
        MenuUiState(
            players = listOf(
                "Player 1",
                "Player 2",
            ),
            withFriends = true
        ),
        MenuUiState(
            players = listOf(
                "Player 1",
                "Player 2",
                ""
            ),
            withFriends = true
        )
    )
}
