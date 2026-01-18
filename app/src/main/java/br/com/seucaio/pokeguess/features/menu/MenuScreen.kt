package br.com.seucaio.pokeguess.features.menu

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessOutlinedButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessScaffold
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessTopAppBar
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.GameSettings
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.features.menu.components.PlayerNameSection
import br.com.seucaio.pokeguess.features.menu.components.SettingsSection
import br.com.seucaio.pokeguess.features.menu.preview.MenuScreenPreviewProvider
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiAction
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiEvent
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState.Companion.toGameSettings
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreen(
    onNavigateToGame: (settings: GameSettings) -> Unit,
    onNavigateToPokemons: (Generation) -> Unit,
    onNavigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = koinViewModel()
) {
    val latestOnNavigateToGame by rememberUpdatedState(onNavigateToGame)
    val latestOnNavigateToPokemons by rememberUpdatedState(onNavigateToPokemons)
    val latestOnNavigateToBack by rememberUpdatedState(onNavigateToBack)

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is MenuUiEvent.NavigateToGame -> {
                    latestOnNavigateToGame(state.toGameSettings())
                }

                is MenuUiEvent.NavigateToPokemons -> {
                    latestOnNavigateToPokemons(state.selectedGeneration)
                }

                is MenuUiEvent.NavigateToBack -> {
                    latestOnNavigateToBack()
                }
            }
        }
    }

    MenuContent(
        onAction = viewModel::handleAction,
        onState = state,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuContent(
    onAction: (MenuUiAction) -> Unit,
    onState: MenuUiState,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()

    PokeGuessScaffold(
        modifier = modifier,
        topAppBar = {
            PokeGuessTopAppBar(
                title = stringResource(R.string.settings),
                onBackButtonClick = { onAction(MenuUiAction.BackButtonClicked) }
            )
        },
        topContent = {
            SettingsSection(
                menuState = onState,
                onGenerationSelect = { onAction(MenuUiAction.GenerationSelected(it)) },
                onTimerToggle = { onAction(MenuUiAction.TimerToggled(it)) },
                onRoundsChange = { onAction(MenuUiAction.NumberOfRoundsChanged(it)) },
                onBottomSheetVisibilityChange = {
                    onAction(MenuUiAction.PlayersBottomSheetVisibilityChanged(true))
                }
            )
        },
        centerContent = {
        },
        bottomContent = {
            PokeGuessOutlinedButton(
                text = stringResource(R.string.pokemon_list),
                onClick = { onAction(MenuUiAction.PokemonListClicked) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            PokeGuessButton(
                text = stringResource(R.string.start_game),
                color = MaterialTheme.colorScheme.secondary,
                enabled = onState.startGameIsAvailable,
                onClick = { onAction(MenuUiAction.StartGameClicked) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )

    if (onState.showPlayersBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onAction(MenuUiAction.PlayersBottomSheetVisibilityChanged(false)) },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier
        ) {
            PlayerNameSection(
                uiState = onState,
                onAction = onAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun MenuScreenPreview(
    @PreviewParameter(MenuScreenPreviewProvider::class) uiState: MenuUiState
) {
    PokeGuessTheme {
        MenuContent(
            onState = uiState,
            onAction = {},
        )
    }
}
