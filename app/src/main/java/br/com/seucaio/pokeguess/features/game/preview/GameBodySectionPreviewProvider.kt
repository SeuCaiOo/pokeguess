package br.com.seucaio.pokeguess.features.game.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.Difficulty
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.game.model.GameUi
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

class GameBodySectionPreviewProvider : PreviewParameterProvider<GameUiState> {
    override val values: Sequence<GameUiState> = sequenceOf(
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "...",
                randomNames = listOf(
                    "Pikachu",
                    "Raichu",
                    "Sandshrew",
                    "Vulpix"
                )
            ),
            showGuessBottomSheet = true,
            gameUi = GameUi(
                difficulty = Difficulty.EASY
            )
        ),
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "...",
                randomNames = listOf(
                    "Pikachu",
                    "Raichu",
                    "Sandshrew",
                    "Vulpix"
                )
            ),
            showGuessBottomSheet = true,
            gameUi = GameUi(
                difficulty = Difficulty.EASY
            )
        ),
        GameUiState(
            pokemon = Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "...",
                randomNames = listOf(
                    "Pikachu",
                    "Raichu",
                    "Sandshrew",
                    "Vulpix"
                )
            ),
            showGuessBottomSheet = true,
            gameUi = GameUi(
                difficulty = Difficulty.MEDIUM
            )
        ),
    )
}
