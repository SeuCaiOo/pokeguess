package br.com.seucaio.pokeguess.features.game.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.game.preview.GuessSectionPreviewProvider
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiAction
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

@Composable
fun GuessSection(
    uiState: GameUiState,
    uiAction: (GameUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val guessSubmitted = uiState.gameUi.guessSubmitted

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonFrame(uiState.toPokemonFrameData())
        Spacer(modifier = Modifier.height(16.dp))
        GuessCardItem(
            guess = uiState.guessTyped,
            guessSubmitted = guessSubmitted,
            guessSkipped = uiState.skipGuess,
            onClickGuess = { uiAction(GameUiAction.GuessBottomSheetVisibilityChanged(true)) },
        )
    }
}

@Composable
private fun GuessCardItem(
    guess: String,
    guessSkipped: Boolean,
    guessSubmitted: Boolean,
    onClickGuess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .then(if (!guessSubmitted) Modifier.clickable(onClick = onClickGuess) else Modifier),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .padding(end = 16.dp)
            ) {
                val label = guess.ifEmpty {
                    if (guessSkipped) {
                        stringResource(R.string.i_don_known)
                    } else {
                        stringResource(R.string.who_that_pokemon)
                    }
                }

                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Icon(
                imageVector = if (guessSubmitted) Icons.Default.Check else Icons.Default.Edit,
                contentDescription = null
            )
        }
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
