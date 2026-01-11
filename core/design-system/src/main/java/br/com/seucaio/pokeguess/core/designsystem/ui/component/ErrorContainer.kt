package br.com.seucaio.pokeguess.core.designsystem.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.core.designsystem.R
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme

@Composable
fun PokeGuessErrorContent(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.oops_something_went_wrong),
    message: String = stringResource(R.string.please_try_again),
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        onRetry?.let {
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.try_again))
            }
        }

    }
}

@PreviewLightDark
@Composable
private fun PokeGuessErrorContentPreview() {
    PokeGuessTheme {
        Surface {
            PokeGuessErrorContent() {}
        }
    }
}
