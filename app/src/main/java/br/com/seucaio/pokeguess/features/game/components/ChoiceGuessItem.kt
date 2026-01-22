package br.com.seucaio.pokeguess.features.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.game.preview.ChoiceGuessItemPreviewProvider
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiAction
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

@Composable
fun ChoiceGuessItem(
    uiState: GameUiState,
    uiAction: (GameUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val choices = uiState.pokemon?.randomNames.orEmpty()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            GuessItemHeader(uiState.selectedPlayer)
            Spacer(modifier = Modifier.height(24.dp))
            ChoicesGrid(
                choices = choices,
                onChoiceSelected = { uiAction(GameUiAction.FillGuess(it)) }
            )
        }
    }
}

@Composable
private fun ChoicesGrid(
    choices: List<String>,
    onChoiceSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        choices.chunked(2).forEach { rowChoices ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowChoices.forEach { choice ->
                    ChoiceButton(
                        text = choice,
                        onClick = { onChoiceSelected(choice) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChoiceButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(64.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ChoiceGuessItemPreview(
    @PreviewParameter(ChoiceGuessItemPreviewProvider::class) uiState: GameUiState
) {
    PokeGuessTheme {
        Surface {
            ChoiceGuessItem(
                uiState = uiState,
                uiAction = {}
            )
        }
    }
}
