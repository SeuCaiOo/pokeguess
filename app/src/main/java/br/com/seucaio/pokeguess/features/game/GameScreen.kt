package br.com.seucaio.pokeguess.features.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessContainer
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessErrorContent
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokemonFrame
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.GreenPokeQuiz
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.game.model.GameUi
import br.com.seucaio.pokeguess.features.game.preview.GameUiStatePreviewProvider
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiAction
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiEvent
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState
import br.com.seucaio.pokeguess.features.game.viewmodel.GameViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onGameOver: (Int, Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val latestOnGameOver by rememberUpdatedState(onGameOver)

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is GameUiEvent.GameOver -> {
                    latestOnGameOver(event.score, event.total, event.withFriends)
                }

                is GameUiEvent.NavigateBack -> {
                    // Handle back navigation if needed, or if it's handled by activity/nav
                    // controller
                }
            }
        }
    }

    // Reset guess input is tricky with UDF if state doesn't explicit clear it.
    // For now, we can rely on ViewModel state updates. Ideally ViewModel holds current guess.
    // But to minimize refactor scope, let's keep guess state local but reactive to pokemon changes.
    var guess by remember { mutableStateOf("") }

    // Reset guess when pokemon changes (New ID or similar check would be better, but Object
    // reference works for now)
    LaunchedEffect(uiState.pokemon) {
        if (uiState.pokemon != null) {
            guess = ""
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
            GameSuccessContent(
                uiState = uiState,
                onGuessChange = { uiAction(GameUiAction.GuessChanged(it)) },
                onCheckGuess = { uiAction(GameUiAction.SubmitGuess(it)) },
                onNextPokemon = { uiAction(GameUiAction.NextPokemon) },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun GameSuccessContent(
    uiState: GameUiState,
    onGuessChange: (String) -> Unit,
    onCheckGuess: (String) -> Unit,
    onNextPokemon: () -> Unit,
    modifier: Modifier = Modifier
) {
    PokeGuessContainer(
        modifier = modifier,
        topContent = { GameHeader(uiState.gameUi) },
        centerContent = {
            GameBody(
                uiState = uiState,
                guess = uiState.guessTyped,
                onGuessChange = onGuessChange,
                onCheckGuess = onCheckGuess
            )
        },
        bottomContent = {
            if (uiState.gameUi.guessSubmitted) {
                PokeGuessButton(
                    text = stringResource(R.string.next),
                    color = GreenPokeQuiz,
                    onClick = onNextPokemon,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                PokeGuessButton(
                    text = stringResource(R.string.submit),
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = { onCheckGuess(uiState.guessTyped) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Composable
private fun GameHeader(gameUi: GameUi) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Round: ${gameUi.progressText}")
            Text("Score: ${gameUi.score}")
        }

        if (gameUi.isTimerEnabled) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Time: ${gameUi.remainingTime}s")
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

@Composable
private fun GameBody(
    uiState: GameUiState,
    guess: String,
    onGuessChange: (String) -> Unit,
    onCheckGuess: (String) -> Unit
) {
    val guessSubmitted = uiState.gameUi.guessSubmitted

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonFrame(uiState.toPokemonFrameData())
        OutlinedTextField(
            value = guess,
            onValueChange = onGuessChange,
            label = { Text(stringResource(R.string.who_that_pokemon)) },
            singleLine = true,
            maxLines = 1,
            readOnly = guessSubmitted,
            keyboardActions = KeyboardActions(onDone = { onCheckGuess(guess) }),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                platformImeOptions = null,
                showKeyboardOnFocus = null,
                hintLocales = null
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun GameScreenPreview(
    @PreviewParameter(GameUiStatePreviewProvider::class) uiState: GameUiState
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
