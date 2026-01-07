package br.com.seucaio.pokeguess.features.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessScaffold
import br.com.seucaio.pokeguess.core.designsystem.ui.component.SettingsItem
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiAction
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiEvent
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreen(
    onNavigateToGame: (Generation, Boolean, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = koinViewModel()
) {
    val latestOnNavigateToGame by rememberUpdatedState(onNavigateToGame)

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is MenuUiEvent.NavigateToGame -> {
                    latestOnNavigateToGame(state.selectedGeneration, state.timerEnabled, state.withFriends)
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

@Composable
fun MenuContent(
    onAction: (MenuUiAction) -> Unit,
    onState: MenuUiState,
    modifier: Modifier = Modifier,
) {
    PokeGuessScaffold(
        modifier = modifier,
        centerContent = {
            Text(
                text = "PokéGuess",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(48.dp))
            SettingsSection(
                menuState = onState,
                onGenerationSelect = { onAction(MenuUiAction.GenerationSelected(it)) },
                onTimerToggle = { onAction(MenuUiAction.TimerToggled(it)) },
                onRoundsChange = { onAction(MenuUiAction.NumberOfRoundsChanged(it)) }
            )
        },
        bottomContent = {
            Spacer(modifier = Modifier.height(48.dp))
            PokeGuessButton(
                text = stringResource(R.string.start_game),
                color = MaterialTheme.colorScheme.secondary,
                onClick = { onAction(MenuUiAction.StartGameClicked) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
private fun SettingsSection(
    menuState: MenuUiState,
    modifier: Modifier = Modifier,
    onGenerationSelect: (Generation) -> Unit = {},
    onTimerToggle: (Boolean) -> Unit = {},
    onRoundsChange: (Int) -> Unit = {},
) {
    val latestOnRoundsChange by rememberUpdatedState(onRoundsChange)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        GenerationSelector(
            selectedGeneration = menuState.selectedGeneration,
            onGenerationSelect = onGenerationSelect
        )

        Spacer(modifier = Modifier.height(16.dp))
        TimerToggle(
            timerEnabled = menuState.timerEnabled,
            onTimerToggle = onTimerToggle
        )

        Spacer(modifier = Modifier.height(16.dp))
        NumberRounds(
            rounds = menuState.rounds,
            onRoundsChange = latestOnRoundsChange
        )
    }
}

@Composable
private fun GenerationSelector(
    selectedGeneration: Generation,
    onGenerationSelect: (Generation) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        SettingsItem(
            title = stringResource(R.string.generation),
            description = selectedGeneration.displayName,
            onClick = { expanded = true }
        ) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Generation.entries.forEach { generation ->
                DropdownMenuItem(
                    text = { Text(generation.displayName) },
                    onClick = {
                        onGenerationSelect(generation)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun TimerToggle(
    timerEnabled: Boolean,
    onTimerToggle: (Boolean) -> Unit
) {
    val description = if (timerEnabled) {
        stringResource(R.string.seconds_per_round)
    } else {
        stringResource(R.string.no_time_limit)
    }
    SettingsItem(
        onClick = { onTimerToggle(!timerEnabled) },
        title = stringResource(R.string.game_timer),
        description = description
    ) {
        Switch(checked = timerEnabled, onCheckedChange = onTimerToggle)
    }
}

@Composable
private fun NumberRounds(
    rounds: Int,
    onRoundsChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val latestOnRoundsChange by rememberUpdatedState(onRoundsChange)
    SettingsItem(
        modifier = modifier,
        title = stringResource(R.string.number_of_rounds),
        description = stringResource(R.string.total_guesses),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.outlineVariant,
                ),
                enabled = rounds > 1,
                onClick = { latestOnRoundsChange(rounds - 1) }
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = null)
            }
            Text(
                text = rounds.toString(),
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.outlineVariant,
                ),
                enabled = rounds < 10,
                onClick = { latestOnRoundsChange(rounds + 1) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun MenuScreenPreview() {
    PokeGuessTheme {
        MenuContent(
            onAction = {},
            onState = MenuUiState()
        )
    }
}
