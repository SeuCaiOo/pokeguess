package br.com.seucaio.pokeguess.features.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
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
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.game.preview.GameHeaderSectionPreviewProvider
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

@Composable
fun GameHeaderSection(uiState: GameUiState, modifier: Modifier = Modifier) {
    val gameUi = uiState.gameUi
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.round_label, gameUi.progressText))
        }

        if (gameUi.isTimerEnabled) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.time_label, gameUi.remainingTime))
                Spacer(modifier = Modifier.width(8.dp))
                LinearProgressIndicator(
                    progress = { gameUi.remainingTime / 10f },
                    modifier = Modifier.fillMaxWidth(),
                    color = ProgressIndicatorDefaults.linearColor,
                    trackColor = ProgressIndicatorDefaults.linearTrackColor,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun GameHeaderSectionPreview(
    @PreviewParameter(GameHeaderSectionPreviewProvider::class) uiState: GameUiState
) {
    PokeGuessTheme { Surface { GameHeaderSection(uiState = uiState) } }
}
