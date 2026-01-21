package br.com.seucaio.pokeguess.features.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.GreenPokeQuiz
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme

@Composable
fun GuessItemHeader(
    playerName: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = playerName,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.who_that_pokemon),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PlayerAvatarItem(
    playerName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(GreenPokeQuiz.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        val initials = if (playerName.contains(" ")) {
            playerName.split(" ")
                .filter { it.isNotEmpty() }
                .map { it.first().uppercase() }
                .take(2)
                .joinToString("")
        } else if (playerName.length >= 2) {
            playerName.take(2).uppercase()
        } else {
            playerName.uppercase()
        }

        Text(
            text = initials.ifEmpty { "?" },
            color = GreenPokeQuiz,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@PreviewLightDark
@Composable
private fun GuessItemHeaderPreview() {
    PokeGuessTheme {
        Surface {
            GuessItemHeader(playerName = "Player 4")
        }
    }
}
