package br.com.seucaio.pokeguess.core.designsystem.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme

@Composable
fun PokeGuessLoadingContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { CircularProgressIndicator() }
}

@PreviewLightDark
@Composable
private fun PokeGuessLoadingContentPreview() {
    PokeGuessTheme {
        Surface {
            PokeGuessLoadingContent()
        }
    }
}
