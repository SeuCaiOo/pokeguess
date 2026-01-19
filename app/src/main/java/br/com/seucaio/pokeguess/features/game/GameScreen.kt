package br.com.seucaio.pokeguess.features.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessErrorContent
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessScaffold
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessTopAppBar
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.GreenPokeQuiz
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.game.components.GameBodySection
import br.com.seucaio.pokeguess.features.game.components.GameHeaderSection
import br.com.seucaio.pokeguess.features.game.preview.GameScreenPreviewProvider
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiAction
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiEvent
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState
import br.com.seucaio.pokeguess.features.game.viewmodel.GameViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onGameOver: (Int, Int, Boolean) -> Unit,
    onNavigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val latestOnGameOver by rememberUpdatedState(onGameOver)
    val latestOnNavigateToBack by rememberUpdatedState(onNavigateToBack)

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is GameUiEvent.GameOver -> {
                    latestOnGameOver(event.score, event.total, event.withFriends)
                }

                is GameUiEvent.NavigateBack -> latestOnNavigateToBack()
            }
        }
    }

    GameScreenContent(
        uiState = uiState,
        uiAction = viewModel::handleAction,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreenContent(
    uiState: GameUiState,
    uiAction: (GameUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    PokeGuessScaffold(
        modifier = modifier,
        topAppBar = {
            PokeGuessTopAppBar(
                onBackButtonClick = { uiAction(GameUiAction.OnBackPressed) }
            )
        },
        topContent = {
            if (uiState.pokemon == null) return@PokeGuessScaffold
            GameHeaderSection(uiState)
        },
        centerContent = {
            when {
                uiState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) { CircularProgressIndicator() }
                }

                uiState.errorMessage != null -> {
                    PokeGuessErrorContent(
                        message = uiState.errorMessage,
                        onRetry = { uiAction(GameUiAction.StartGame) },
                    )
                }

                uiState.pokemon != null -> {
                    GameBodySection(
                        uiState = uiState,
                        uiAction = uiAction,
                    )
                }
            }
        },
        bottomContent = {
            if (uiState.pokemon == null) return@PokeGuessScaffold
            if (uiState.gameUi.guessSubmitted) {
                PokeGuessButton(
                    text = stringResource(R.string.next),
                    color = GreenPokeQuiz,
                    onClick = { uiAction(GameUiAction.NextPokemon) },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                PokeGuessButton(
                    text = stringResource(R.string.submit),
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = { uiAction(GameUiAction.SubmitGuess(uiState.guessTyped)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun GameScreenPreview(
    @PreviewParameter(GameScreenPreviewProvider::class) uiState: GameUiState
) {
    PokeGuessTheme {
        Surface {
            GameScreenContent(
                uiState = uiState,
                uiAction = {}
            )
        }
    }
}
