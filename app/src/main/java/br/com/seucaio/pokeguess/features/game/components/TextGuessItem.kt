package br.com.seucaio.pokeguess.features.game.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.game.preview.TextGuessItemPreviewProvider
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiAction
import br.com.seucaio.pokeguess.features.game.viewmodel.GameUiState

@Composable
fun TextGuessItem(
    uiState: GameUiState,
    uiAction: (GameUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val guess = uiState.guessTyped

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.who_that_pokemon),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        GuessTextField(
            guess = guess,
            onGuessChange = { newValue -> uiAction(GameUiAction.GuessChanged(newValue)) },
            onSubmitGuess = { uiAction(GameUiAction.SubmitGuess(guess)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        PokeGuessButton(
            text = stringResource(uiState.buttonConfirmRes),
            color = MaterialTheme.colorScheme.secondary,
            onClick = { uiAction(GameUiAction.SubmitGuess(guess)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun GuessTextField(
    guess: String,
    onGuessChange: (String) -> Unit,
    onSubmitGuess: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    OutlinedTextField(
        value = guess,
        onValueChange = { newValue -> onGuessChange(newValue) },
        placeholder = { Text(stringResource(R.string.insert_your_guess)) },
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions(onDone = { onSubmitGuess(guess) }),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            platformImeOptions = null,
            showKeyboardOnFocus = null,
            hintLocales = null
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .focusRequester(focusRequester)
    )
}

@PreviewLightDark
@Composable
private fun TextGuessItemPreview(
    @PreviewParameter(TextGuessItemPreviewProvider::class) uiState: GameUiState
) {
    PokeGuessTheme {
        Surface {
            TextGuessItem(
                uiState = uiState,
                uiAction = {}
            )
        }
    }
}
