package br.com.seucaio.pokeguess.features.score.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.AccuracyLevel
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.score.model.GameStatsUi
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiState

class ScorePokemonListPreviewProvider : PreviewParameterProvider<ScoreUiState> {
    override val values: Sequence<ScoreUiState> = sequenceOf(
        ScoreUiState(
            gameMatch = GameMatch(
                id = 1,
                totalRounds = 10,
                roundsMultiplayer = mapOf(
                    1 to mapOf(
                        "Player 1" to "Bulbasaur",
                        "Player 2" to "Pikachu",
                    ),
                    2 to mapOf(
                        "Player 1" to "Charmander",
                        "Player 2" to "Bulbasaur",
                    ),
                    3 to mapOf(
                        "Player 1" to "Pikachu",
                        "Player 2" to "Charmander",
                    )
                ),
                pokemons = listOf(
                    Pokemon(
                        id = 1,
                        name = "Bulbasaur",
                        imageUrl = ""
                    ),
                    Pokemon(
                        id = 2,
                        name = "Pikachu",
                        imageUrl = ""
                    ),
                    Pokemon(
                        id = 3,
                        name = "Charmander",
                        imageUrl = ""
                    )
                )
            )
        ),
        ScoreUiState(

        ),
        ScoreUiState(

        ),
        ScoreUiState(
        )
    )
}
