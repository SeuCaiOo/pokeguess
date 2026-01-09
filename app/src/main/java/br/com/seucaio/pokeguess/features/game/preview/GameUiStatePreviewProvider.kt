package br.com.seucaio.pokeguess.features.game.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.game.model.GameUi
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

class GameUiStatePreviewProvider : PreviewParameterProvider<GameUiState> {
    override val values: Sequence<GameUiState> = sequenceOf(
        GameUiState(
            isLoading = false,
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gameUi = GameUi(
                remainingTime = 7,
                isTimerEnabled = true,
            )
        ),
        GameUiState(
            isLoading = false,
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gameUi = GameUi(
                remainingTime = 7,
                isTimerEnabled = true,
                guessSubmitted = true,
                correctGuess = true
            )
        ),
        GameUiState(
            isLoading = false,
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "..."
            ),
            gameUi = GameUi(
                remainingTime = 7,
                isTimerEnabled = true,
                guessSubmitted = true,
                correctGuess = false
            )
        ),
        GameUiState(
            isLoading = true,
            gameUi = GameUi(
                remainingTime = 10,
            )
        ),
        GameUiState(
            isLoading = false,
            errorMessage = "Failed to load Pokémon",
            gameUi = GameUi()
        )
    )
}
