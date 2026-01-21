package br.com.seucaio.pokeguess.features.game.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.Difficulty
import br.com.seucaio.pokeguess.features.game.preview.GameBodySectionPreviewProvider
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiAction
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameBodySection(
    uiState: GameUiState,
    uiAction: (GameUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()

    GuessSection(
        uiState = uiState,
        uiAction = uiAction,
        modifier = modifier
    )

    if (uiState.showGuessBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { uiAction(GameUiAction.GuessBottomSheetVisibilityChanged(false)) },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier
        ) {
            if (uiState.gameUi.difficulty != Difficulty.HARD) {
                ChoiceGuessItem(
                    uiState = uiState,
                    uiAction = uiAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            } else {
                TextGuessItem(
                    uiState = uiState,
                    uiAction = uiAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun GameBodySectionPreview(
    @PreviewParameter(GameBodySectionPreviewProvider::class) uiState: GameUiState
) {
    PokeGuessTheme {
        Surface {
            GameBodySection(
                uiState = uiState,
                uiAction = {}
            )
        }
    }
}
