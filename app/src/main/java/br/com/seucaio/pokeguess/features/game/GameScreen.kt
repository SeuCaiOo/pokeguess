package br.com.seucaio.pokeguess.features.game

import android.content.res.Configuration
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.model.Pokemon
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onGameOver: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    generation: Generation = Generation.I,
    timerEnabled: Boolean = false,
    viewModel: GameViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val gameState by viewModel.gameState.collectAsState()
    var guess by remember { mutableStateOf("") }
    val currentOnGameOver by rememberUpdatedState(onGameOver)

    LaunchedEffect(Unit) {
        viewModel.startGame(generation, timerEnabled)
    }

    LaunchedEffect(gameState.isGameOver) {
        if (gameState.isGameOver) {
            currentOnGameOver(gameState.score, gameState.totalRounds)
        }
    }

    // Reset guess when pokemon changes
    LaunchedEffect(uiState) {
        if (uiState is GameUiState.Success) {
            guess = ""
        }
    }

    GameScreenContent(
        uiState = uiState,
        gameState = gameState,
        guess = guess,
        onGuessChange = { guess = it },
        onCheckGuess = { viewModel.checkGuess(it) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreenContent(
    uiState: GameUiState,
    gameState: GameState,
    guess: String,
    onGuessChange: (String) -> Unit,
    onCheckGuess: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GameHeader(gameState)

        Spacer(modifier = Modifier.height(32.dp))

        GameBody(
            uiState = uiState,
            guess = guess,
            onGuessChange = onGuessChange,
            onCheckGuess = onCheckGuess
        )
    }
}

@Composable
private fun GameHeader(gameState: GameState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Round: ${gameState.currentRound}/${gameState.totalRounds}")
            Text("Score: ${gameState.score}")
        }

        if (gameState.isTimerEnabled) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Time: ${gameState.remainingTime}s")
                Spacer(modifier = Modifier.width(8.dp))
                LinearProgressIndicator(
                    progress = { gameState.remainingTime / 10f },
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
    when (uiState) {
        is GameUiState.Loading -> {
            CircularProgressIndicator()
        }

        is GameUiState.Success -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = uiState.pokemon.imageUrl,
                    contentDescription = "Pokémon Silhouette",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.Black)
                )

                OutlinedTextField(
                    value = guess,
                    onValueChange = onGuessChange,
                    label = { Text(stringResource(R.string.who_that_pokemon)) },
                    singleLine = true,
                    maxLines = 1,
                    keyboardActions = KeyboardActions(onDone = { onCheckGuess(guess) }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Button(
                    onClick = { onCheckGuess(guess) },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Submit")
                }
            }
        }

        is GameUiState.Error -> {
            Text(text = uiState.message)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GameScreenPreviewSuccess() {
    PokeGuessTheme {
        Surface {
            GameScreenContent(
                uiState = GameUiState.Success(
                    Pokemon(
                        id = 1,
                        name = "Pikachu",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/" +
                            "sprites/master/sprites/pokemon/other/official-artwork/25.png"
                    )
                ),
                gameState = GameState(
                    currentRound = 1,
                    totalRounds = 10,
                    score = 0,
                    isTimerEnabled = true,
                    remainingTime = 7,
                    isGameOver = false
                ),
                guess = "Pikachu",
                onGuessChange = {},
                onCheckGuess = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GameScreenPreviewLoading() {
    PokeGuessTheme {
        Surface {
            GameScreenContent(
                uiState = GameUiState.Loading,
                gameState = GameState(
                    currentRound = 1,
                    totalRounds = 10,
                    score = 0,
                    isTimerEnabled = false,
                    remainingTime = 10,
                    isGameOver = false
                ),
                guess = "",
                onGuessChange = {},
                onCheckGuess = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GameScreenPreviewError() {
    PokeGuessTheme {
        Surface {
            GameScreenContent(
                uiState = GameUiState.Error("Failed to load Pokémon"),
                gameState = GameState(
                    currentRound = 1,
                    totalRounds = 10,
                    score = 0,
                    isTimerEnabled = false,
                    remainingTime = 10,
                    isGameOver = false
                ),
                guess = "",
                onGuessChange = {},
                onCheckGuess = {}
            )
        }
    }
}
