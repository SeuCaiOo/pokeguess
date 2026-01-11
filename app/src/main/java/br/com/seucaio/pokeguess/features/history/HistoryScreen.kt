package br.com.seucaio.pokeguess.features.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessContainer
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessErrorContent
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessLoadingContent
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.features.history.preview.HistoryUiStatePreviewProvider
import br.com.seucaio.pokeguess.features.history.viewmodel.HistoryUiAction
import br.com.seucaio.pokeguess.features.history.viewmodel.HistoryUiEvent
import br.com.seucaio.pokeguess.features.history.viewmodel.HistoryUiState
import br.com.seucaio.pokeguess.features.history.viewmodel.HistoryViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    onNavigateToScore: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val latestOnNavigateToScore by rememberUpdatedState(onNavigateToScore)
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HistoryUiEvent.NavigateToScoreByMatchId -> latestOnNavigateToScore(event.matchId)
            }
        }
    }

    HistoryContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun HistoryContent(
    uiState: HistoryUiState,
    onAction: (HistoryUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    PokeGuessContainer(
        modifier = modifier,
        topContent = {},
        centerContent = {
            when {
                uiState.isLoading -> {
                    PokeGuessLoadingContent()
                }
                uiState.errorMessage != null -> {
                    PokeGuessErrorContent(message = uiState.errorMessage)
                }
                uiState.matches.isEmpty() -> {
                    PokeGuessErrorContent(message = stringResource(R.string.no_history_found))
                }
                else -> {
                    HistoryList(matches = uiState.matches)
                }
            }
        }
    )
}

@Composable
private fun HistoryList(matches: List<GameMatch>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(matches) { match ->
            HistoryItem(match = match)
        }
    }
}

@Composable
private fun HistoryItem(match: GameMatch) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date = match.finishedAt?.let { dateFormat.format(Date(it)) } ?: ""

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = match.playerName ?: "Player",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
            Text(
                text = stringResource(
                    R.string.match_result_format,
                    match.score ?: 0,
                    match.totalRounds
                ),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun EmptyHistoryMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_history_found),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@PreviewLightDark
@Composable
private fun ScoreScreenPreview(
    @PreviewParameter(HistoryUiStatePreviewProvider ::class) uiState: HistoryUiState,
) {
    PokeGuessTheme {
        HistoryContent(
            uiState = uiState,
            onAction = {},
        )
    }
}

