package br.com.seucaio.pokeguess.features.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessOutlinedButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessScaffold
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.HighAccuracyColor
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.LowAccuracyColor
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.score.model.GameStatsUi
import br.com.seucaio.pokeguess.features.score.preview.ScoreUiStatePreviewProvider
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiAction
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiEvent
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiState
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScoreScreen(
    onPlayAgain: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScoreViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val latestOnPlayAgain by rememberUpdatedState(onPlayAgain)
    val latestOnBackToHome by rememberUpdatedState(onBackToHome)

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ScoreUiEvent.NavigateToMenu -> latestOnPlayAgain()
                is ScoreUiEvent.NavigateToHome -> latestOnBackToHome()
            }
        }
    }

    ScoreContent(
        modifier = modifier,
        onAction = viewModel::handleAction,
        uiState = uiState,
    )
}

@Composable
fun ScoreContent(
    uiState: ScoreUiState,
    onAction: (ScoreUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    PokeGuessScaffold(
        modifier = modifier,
        topContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.game_over),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        },
        centerContent = {
            ScoreResultCard(gameStatsUi = uiState.gameStatsUi)
        },
        bottomContent = {
            ScoreActionButtons(
                onPlayAgain = { onAction(ScoreUiAction.PlayAgainClicked) },
                onBackToHome = { onAction(ScoreUiAction.BackToHomeClicked) }
            )
        }
    )
}

@Composable
private fun ScoreResultCard(
    gameStatsUi: GameStatsUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            with(gameStatsUi) {
                Text(
                    text = stringResource(gameStatsUi.accuracyValueRes, accuracy),
                    style = MaterialTheme.typography.displayLarge,
                    color = gameStatsUi.accuracyColor
                )
                Text(
                    text = stringResource(R.string.accuracy),
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        label = stringResource(R.string.correct),
                        value = score.toString(),
                        color = HighAccuracyColor
                    )
                    StatItem(
                        label = stringResource(R.string.incorrect),
                        value = incorrect.toString(),
                        color = LowAccuracyColor
                    )
                    StatItem(
                        label = stringResource(R.string.total),
                        value = total.toString(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ScoreActionButtons(onPlayAgain: () -> Unit, onBackToHome: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PokeGuessButton(
            text = stringResource(R.string.play_again),
            color = MaterialTheme.colorScheme.secondary,
            onClick = onPlayAgain,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PokeGuessOutlinedButton(
            text = stringResource(R.string.back_to_home),
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, style = MaterialTheme.typography.headlineMedium, color = color)
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}

@PreviewLightDark
@Composable
private fun ScoreScreenPreview(
    @PreviewParameter(ScoreUiStatePreviewProvider::class) uiState: ScoreUiState,
) {
    PokeGuessTheme {
        ScoreContent(
            uiState = uiState,
            onAction = {},
        )
    }
}
