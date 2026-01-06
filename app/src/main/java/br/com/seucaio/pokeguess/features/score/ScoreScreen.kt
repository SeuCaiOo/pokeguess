package br.com.seucaio.pokeguess.features.score

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import kotlin.math.roundToInt

@Composable
fun ScoreScreen(
    score: Int,
    total: Int,
    onPlayAgain: () -> Unit,
    onBackToMenu: () -> Unit
) {
    val accuracy = if (total > 0) (score.toFloat() / total * 100).roundToInt() else 0
    val incorrect = total - score

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Game Over!",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$accuracy%",
                    style = MaterialTheme.typography.displayLarge,
                    color = when {
                        accuracy >= 80 -> Color(0xFF4CAF50)
                        accuracy >= 50 -> Color(0xFFFFC107)
                        else -> Color(0xFFF44336)
                    }
                )
                Text(text = "Accuracy", style = MaterialTheme.typography.labelLarge)

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(label = "Correct", value = score.toString(), color = Color(0xFF4CAF50))
                    StatItem(label = "Incorrect", value = incorrect.toString(), color = Color(0xFFF44336))
                    StatItem(
                        label = "Total",
                        value = total.toString(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Play Again")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBackToMenu,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Back to Menu")
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.headlineMedium, color = color)
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScoreScreenPreview() {
    PokeGuessTheme {
        Surface {
            ScoreScreen(
                score = 8,
                total = 10,
                onPlayAgain = {},
                onBackToMenu = {}
            )
        }
    }
}
