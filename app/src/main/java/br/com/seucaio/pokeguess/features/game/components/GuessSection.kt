package br.com.seucaio.pokeguess.features.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokemonFrame
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.GreenPokeQuiz
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.RedPokeQuiz
import br.com.seucaio.pokeguess.features.game.model.RoundPlayerUi
import br.com.seucaio.pokeguess.features.game.preview.GuessSectionPreviewProvider
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiAction
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

@Composable
fun GuessSection(
    uiState: GameUiState,
    uiAction: (GameUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonFrame(uiState.toPokemonFrameData())
        Spacer(modifier = Modifier.height(16.dp))
        GuessCardItem(
            uiState = uiState,
            uiAction = uiAction
        )
    }
}

@Composable
private fun GuessCardItem(
    uiState: GameUiState,
    uiAction: (GameUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val players = uiState.roundPlayers
    val multiplayerGame = uiState.multiplayerGame

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items = players, key = { _, item -> item }) { index, player ->
            val colorBackground = if (player.submittedGuess) {
                if (player.correctGuess) {
                    GreenPokeQuiz
                } else {
                    RedPokeQuiz
                }
            } else {
                MaterialTheme.colorScheme.surface
            }

            GuessPlayerItem(
                player = player,
                onChangeBottomSheetVisibility = {
                    uiAction(GameUiAction.GuessBottomSheetVisibilityChanged(true, index))
                },
                multiplayerGame = multiplayerGame,
                modifier = Modifier.background(
                    color = colorBackground,
                    shape = MaterialTheme.shapes.medium
                )
            )
        }
    }
}

@Composable
private fun GuessPlayerItem(
    player: RoundPlayerUi,
    multiplayerGame: Boolean,
    onChangeBottomSheetVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    val guessSubmitted = player.submittedGuess
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .then(
                if (!player.filledGuess) {
                    Modifier.clickable(onClick = onChangeBottomSheetVisibility)
                } else {
                    Modifier
                }
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val playerGuessFilled = player.filledGuess
        val label = when {
            !playerGuessFilled && !guessSubmitted -> stringResource(R.string.who_that_pokemon)
            playerGuessFilled && !guessSubmitted && multiplayerGame -> stringResource(R.string.hidden_guess)
            playerGuessFilled && guessSubmitted && !player.skippedGuess -> player.guess
            else -> stringResource(R.string.i_don_known)
        }

        if (multiplayerGame) PlayerNameTag(player.name)
        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(end = 16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Icon(
            imageVector = if (player.filledGuess) Icons.Default.Check else Icons.Default.Edit,
            contentDescription = null
        )
    }
}

@PreviewLightDark
@Composable
private fun GuessSectionPreview(
    @PreviewParameter(GuessSectionPreviewProvider::class) uiState: GameUiState
) {
    PokeGuessTheme {
        Surface {
            GuessSection(
                uiState = uiState,
                uiAction = {}
            )
        }
    }
}
