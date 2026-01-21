package br.com.seucaio.pokeguess.features.game.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

class ChoiceGuessItemPreviewProvider : PreviewParameterProvider<GameUiState> {
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
            pokemonMatchs = listOf(
                Pokemon(
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
                Pokemon(
                    id = 25,
                    name = "Bulbasaur",
                    imageUrl = "...",
                    randomNames = listOf(
                        "Bulbasaur",
                        "Ivysaur",
                        "Venusaur",
                        "Charmander"
                    )
                ),

            ),
            gamemMatch = GameMatch(
                id = 1,
                players = listOf("Player 1", "Player 2"),
                totalRounds = 5,
            )
        ),
    )
}
