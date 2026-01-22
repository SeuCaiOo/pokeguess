package br.com.seucaio.pokeguess.features.game.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

class TextGuessItemPreviewProvider : PreviewParameterProvider<GameUiState> {
    override val values: Sequence<GameUiState> = sequenceOf(
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gamemMatch = GameMatch(
                id = 1,
                players = listOf("Player 1", "Player 2"),
                totalRounds = 5,
            )
        ),
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            guessTyped = "Pika",
            gamemMatch = GameMatch(
                id = 1,
                players = listOf("Player 1", "Player 2"),
                totalRounds = 5,
            )
        )
    )
}
