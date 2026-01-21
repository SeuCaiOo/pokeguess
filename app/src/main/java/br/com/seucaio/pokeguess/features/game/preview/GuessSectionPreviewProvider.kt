package br.com.seucaio.pokeguess.features.game.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.game.model.GameUi
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

class GuessSectionPreviewProvider : PreviewParameterProvider<GameUiState> {
    override val values: Sequence<GameUiState> = sequenceOf(
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gamemMatch = GameMatch(
                id = 1,
                totalRounds = 5,
                players = listOf("Player 1", "Player 2"),
            )
        ),
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gamemMatch = GameMatch(
                id = 1,
                totalRounds = 5,
                players = listOf("Player 1", "Player 2"),
            ),
            skipGuess = true
        ),
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gamemMatch = GameMatch(
                id = 1,
                totalRounds = 5,
                players = listOf("Player 1", "Player 2"),
            ),
            guessTyped = "Pika"
        ),
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gameUi = GameUi(
                guessSubmitted = true,
                correctGuess = true
            ),
            gamemMatch = GameMatch(
                id = 1,
                totalRounds = 5,
                players = listOf("Player 1", "Player 2"),
            ),
            guessTyped = "Pikachu"
        ),
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gameUi = GameUi(
                guessSubmitted = true,
                correctGuess = false
            ),
            gamemMatch = GameMatch(
                id = 1,
                totalRounds = 5,
                players = listOf("Player 1", "Player 2"),
            ),
            guessTyped = "",
            skipGuess = true
        ),
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gameUi = GameUi(
                guessSubmitted = true,
                correctGuess = false
            ),
            gamemMatch = GameMatch(
                id = 1,
                totalRounds = 5,
                players = listOf("Player 1", "Player 2"),
            ),
            guessTyped = "Pika"
        ),
    )
}
