package br.com.seucaio.pokeguess.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessContainer
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessOutlinedButton
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.home.viewmodel.HomeUiAction
import br.com.seucaio.pokeguess.features.home.viewmodel.HomeUiEvent
import br.com.seucaio.pokeguess.features.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToMenuSolo: () -> Unit,
    onNavigateToMenuFriends: () -> Unit,
    onNavigateToHistory: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val latestOnNavigateToMenuSolo by rememberUpdatedState(onNavigateToMenuSolo)
    val latestOnNavigateToMenuFriends by rememberUpdatedState(onNavigateToMenuFriends)
    val latestOnNavigateToHistory by rememberUpdatedState(onNavigateToHistory)

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeUiEvent.NavigateToSoloMode -> latestOnNavigateToMenuSolo()
                is HomeUiEvent.NavigateToFriendsMode -> latestOnNavigateToMenuFriends()
                is HomeUiEvent.NavigateToHistory -> latestOnNavigateToHistory()
            }
        }
    }

    HomeContent(
        modifier = modifier,
        onAction = viewModel::handleAction
    )
}

@Composable
fun HomeContent(
    onAction: (HomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    PokeGuessContainer(
        modifier = modifier,
        centerContent = { HomeBranding() },
        bottomContent = {
            HomeActions(
                onPlaySolo = { onAction(HomeUiAction.SoloModeSelected) },
                onHistory = { onAction(HomeUiAction.HistorySelected) }
            )
        }
    )
}

@Composable
private fun HomeBranding(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.5f)
                    .aspectRatio(1f),
                painter = painterResource(id = R.mipmap.ic_launcher_monochrome),
                contentDescription = stringResource(R.string.pokemon_logo),
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(stringResource(R.string.pokemon))
                    append(" ")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(R.string.guess))
                }
            },
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.who_that_pokemon),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun HomeActions(
    onPlaySolo: () -> Unit,
    onHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PokeGuessButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.play_solo),
            onClick = onPlaySolo,
        )

        // TODO implement local multiplayer
//        Spacer(modifier = Modifier.height(16.dp))
//        PokeGuessOutlinedButton(
//            modifier = Modifier.fillMaxWidth(),
//            text = stringResource(R.string.play_with_friends),
//            onClick = onPlayWithFriends,
//        )
        Spacer(modifier = Modifier.height(16.dp))
        PokeGuessOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.history),
            onClick = onHistory,
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    PokeGuessTheme {
        HomeContent(
            onAction = {}
        )
    }
}
