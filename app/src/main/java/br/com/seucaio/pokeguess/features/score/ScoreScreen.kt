package br.com.seucaio.pokeguess.features.score

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.HighAccuracyColor
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.LowAccuracyColor
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.MediumAccuracyColor
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import kotlin.math.roundToInt

private const val ACCURACY_PERCENTAGE = 100
private const val HIGH_ACCURACY_THRESHOLD = 80
private const val MEDIUM_ACCURACY_THRESHOLD = 50

@Composable
fun ScoreScreen(
    score: Int,
    total: Int,
    onPlayAgain: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accuracy = if (total > 0) {
        (score.toFloat() / total * ACCURACY_PERCENTAGE).roundToInt()
    } else {
        0
    }
    val incorrect = total - score

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.game_over),
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        ScoreResultCard(accuracy, score, incorrect, total)

        Spacer(modifier = Modifier.height(48.dp))

        ScoreActionButtons(
            onPlayAgain = onPlayAgain,
            onBackToMenu = onBackToHome
        )
    }
}

@Composable
private fun ScoreResultCard(
    accuracy: Int,
    score: Int,
    incorrect: Int,
    total: Int
) {
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
                    accuracy >= HIGH_ACCURACY_THRESHOLD -> HighAccuracyColor
                    accuracy >= MEDIUM_ACCURACY_THRESHOLD -> MediumAccuracyColor
                    else -> LowAccuracyColor
                }
            )
            Text(text = stringResource(R.string.accuracy), style = MaterialTheme.typography.labelLarge)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(label = "Correct", value = score.toString(), color = HighAccuracyColor)
                StatItem(
                    label = stringResource(R.string.incorrect),
                    value = incorrect.toString(),
                    color = LowAccuracyColor
                )
                StatItem(
                    label = stringResource(R.string.total),
                    value = total.toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ScoreActionButtons(
    onPlayAgain: () -> Unit,
    onBackToMenu: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.play_again))
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBackToMenu,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.back_to_home))
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, style = MaterialTheme.typography.headlineMedium, color = color)
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}

@PreviewLightDark
@Composable
private fun ScoreScreenPreview() {
    PokeGuessTheme {
        Surface {
            ScoreScreen(
                score = 8,
                total = 10,
                onPlayAgain = {},
                onBackToHome = {}
            )
        }
    }
}
