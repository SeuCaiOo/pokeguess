package br.com.seucaio.pokeguess.features.score

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessScaffold
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessTopAppBar
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.score.components.ScorePokemonList
import br.com.seucaio.pokeguess.features.score.components.ScoreResultList
import br.com.seucaio.pokeguess.features.score.preview.ScoreScreenPreviewProvider
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiAction
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiEvent
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiState
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScoreScreen(
    onPlayAgain: (Boolean) -> Unit,
    onBackToHome: () -> Unit,
    onNavigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScoreViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val latestOnPlayAgain by rememberUpdatedState(onPlayAgain)
    val latestOnBackToHome by rememberUpdatedState(onBackToHome)
    val latestOnNavigateToBack by rememberUpdatedState(onNavigateToBack)

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ScoreUiEvent.NavigateToMenu -> latestOnPlayAgain(event.withFriends)
                is ScoreUiEvent.NavigateToHome -> latestOnBackToHome()
                is ScoreUiEvent.NavigateToBack -> latestOnNavigateToBack()
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
        topAppBar = {
            PokeGuessTopAppBar(
                title = stringResource(R.string.score),
                onBackButtonClick = { onAction(ScoreUiAction.BackButtonClicked) }
            )
        },
        topContent = {
            ScoreResultList(uiState)
        },
        centerContent = {
            ScorePokemonList(
                uiState = uiState,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        bottomContent = {
            Spacer(modifier = Modifier.height(16.dp))
            ScoreActionButtons(
                onPlayAgain = { onAction(ScoreUiAction.PlayAgainClicked) },
                onBackToHome = { onAction(ScoreUiAction.BackToHomeClicked) }
            )
        }
    )
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
//        Spacer(modifier = Modifier.height(16.dp))
//        PokeGuessOutlinedButton(
//            text = stringResource(R.string.back_to_home),
//            onClick = onBackToHome,
//            modifier = Modifier.fillMaxWidth(),
//        )
    }
}

@PreviewLightDark
@Composable
private fun ScoreScreenPreview(
    @PreviewParameter(ScoreScreenPreviewProvider::class) uiState: ScoreUiState,
) {
    PokeGuessTheme {
        ScoreContent(
            uiState = uiState,
            onAction = {},
        )
    }
}
