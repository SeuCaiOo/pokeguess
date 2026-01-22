package br.com.seucaio.pokeguess.features.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme

@Composable
fun PlayerNameTag(
    playerName: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = playerName,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onTertiary,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(vertical = 4.dp, horizontal = 16.dp)
    )
}

@PreviewLightDark
@Composable
private fun PlayerNameTagPreview() {
    PokeGuessTheme {
        Surface {
            PlayerNameTag(playerName = "Player 4")
        }
    }
}
