package br.com.seucaio.pokeguess.features.game.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.game.model.GameUi
import br.com.seucaio.pokeguess.features.game.model.RoundPlayerUi
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

class GuessCardItemPreviewProvider : PreviewParameterProvider<GameUiState> {
    override val values: Sequence<GameUiState> = sequenceOf(
        GameUiState(
            gamemMatch = GameMatch(
                id = 1,
                totalRounds = 5,
                players = listOf("Player 1", "Player 2"),
            ),
            roundPlayers = listOf(
                RoundPlayerUi(
                    name = "Player 1",
                    guess = "Pikachu",
                    filledGuess = true,
                    correctGuess = true,
                ),
                RoundPlayerUi(
                    name = "Player 2",
                    guess = "Pikachu",
                    filledGuess = false,
                )
            )
        ),
        GameUiState(
            gamemMatch = GameMatch(
                id = 1,
                totalRounds = 5,
                players = listOf("Player 1", "Player 2"),
            ),
            roundPlayers = listOf(
                RoundPlayerUi(
                    name = "Player 1",
                    guess = "Pikachu",
                    filledGuess = true,
                    correctGuess = true,
                    submittedGuess = true,
                ),
                RoundPlayerUi(
                    name = "Player 2",
                    guess = "Pikachu",
                    filledGuess = false,
                    submittedGuess = true,
                )
            )
        ),
    )
}
