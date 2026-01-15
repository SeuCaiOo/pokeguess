package br.com.seucaio.pokeguess.features.score.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.AccuracyLevel
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.score.model.GameStatsUi
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiState

class ScoreUiStatePreviewProvider : PreviewParameterProvider<ScoreUiState> {
    override val values: Sequence<ScoreUiState> = sequenceOf(
        ScoreUiState(
            gameStatsUi = GameStatsUi(
                score = 10,
                total = 10,
                accuracy = 100,
                incorrect = 0,
                accuracyLevel = AccuracyLevel.High
            ),
            gameMatch = GameMatch(
                id = 1,
                playerName = "Player 1",
                score = 10,
                totalRounds = 10,
                rounds = mapOf(
                    1 to "Bulbasaur",
                    2 to "Pikachu",
                    3 to "Charmander"
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
            gameStatsUi = GameStatsUi(
                score = 8,
                total = 10,
                accuracy = 80,
                incorrect = 2,
                accuracyLevel = AccuracyLevel.High
            )
        ),
        ScoreUiState(
            gameStatsUi = GameStatsUi(
                score = 5,
                total = 10,
                accuracy = 50,
                incorrect = 5,
                accuracyLevel = AccuracyLevel.Medium
            )
        ),
        ScoreUiState(
            gameStatsUi = GameStatsUi(
                score = 2,
                total = 10,
                accuracy = 20,
                incorrect = 8,
                accuracyLevel = AccuracyLevel.Low
            )
        )
    )
}
