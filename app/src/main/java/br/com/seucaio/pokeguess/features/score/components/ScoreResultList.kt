package br.com.seucaio.pokeguess.features.score.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.HighAccuracyColor
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.LowAccuracyColor
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.YellowPokeQuiz
import br.com.seucaio.pokeguess.features.game.components.PlayerNameTag
import br.com.seucaio.pokeguess.features.score.model.GameStatsUi
import br.com.seucaio.pokeguess.features.score.preview.ScoreResultListPreviewProvider
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiState

@Composable
fun ScoreResultList(
    uiState: ScoreUiState,
    modifier: Modifier = Modifier,
) {
    val gameStasList = uiState.playerStats
    val scorePlayers = uiState.scorePlayers
    val winningStats = remember(gameStasList) { gameStasList.maxByOrNull { it.accuracy } }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(items = gameStasList, key = { index, _ -> index }) { index, stats ->
            val playerName = scorePlayers.keys.toList().getOrNull(index).orEmpty()
            val winner = winningStats == stats
            val singlePlayer = gameStasList.size == 1

            ScoreResultCard(
                gameStatsUi = stats,
                playerName = playerName,
                winner = winner,
                multiPlayer = !singlePlayer,
                modifier = if (singlePlayer) Modifier.fillParentMaxWidth() else Modifier
            )
        }
    }
}

@Composable
private fun ScoreResultCard(
    gameStatsUi: GameStatsUi,
    playerName: String,
    modifier: Modifier = Modifier,
    winner: Boolean = false,
    multiPlayer: Boolean = false
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            with(gameStatsUi) {
                if(multiPlayer){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PlayerNameTag(
                            playerName = playerName,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        if (winner) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = YellowPokeQuiz
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(gameStatsUi.accuracyValueRes, accuracy),
                    style = MaterialTheme.typography.displayLarge,
                    color = gameStatsUi.accuracyColor,
                )
                Text(
                    text = stringResource(R.string.accuracy),
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                ) {
                    StatItem(
                        label = stringResource(R.string.correct),
                        value = score.toString(),
                        color = HighAccuracyColor
                    )
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
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
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
private fun ScoreResultListPreview(
    @PreviewParameter(ScoreResultListPreviewProvider::class) uiState: ScoreUiState
) {
    PokeGuessTheme {
        Surface {
            ScoreResultList(
                uiState = uiState
            )
        }
    }
}
