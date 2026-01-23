package br.com.seucaio.pokeguess.features.game.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
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
