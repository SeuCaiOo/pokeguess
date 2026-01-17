package br.com.seucaio.pokeguess.features.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessOutlinedButton
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessScaffold
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessTopAppBar
import br.com.seucaio.pokeguess.core.designsystem.ui.component.SettingsItem
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.features.menu.preview.MenuUiStatePreviewProvider
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiAction
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiEvent
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreen(
    onNavigateToGame: (Generation, Boolean, Int, String?, Boolean) -> Unit,
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
                    latestOnNavigateToGame(
                        state.selectedGeneration,
                        state.timerEnabled,
                        state.rounds,
                        state.playerName,
                        state.withFriends
                    )
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

@Composable
fun MenuContent(
    onAction: (MenuUiAction) -> Unit,
    onState: MenuUiState,
    modifier: Modifier = Modifier,
) {
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
                onRoundsChange = { onAction(MenuUiAction.NumberOfRoundsChanged(it)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PokeGuessOutlinedButton(
                text = stringResource(R.string.pokemon_list),
                onClick = { onAction(MenuUiAction.PokemonListClicked) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        centerContent = {
            PlayerNameSection(
                uiState = onState,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomContent = {
            PokeGuessButton(
                text = stringResource(R.string.start_game),
                color = MaterialTheme.colorScheme.secondary,
                enabled = onState.startGameIsAvailable,
                onClick = { onAction(MenuUiAction.StartGameClicked) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun PlayerNameSection(
    uiState: MenuUiState,
    onAction: (MenuUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val players = uiState.players
    val withFriends = uiState.withFriends

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        val label = stringResource(if (withFriends) R.string.players else R.string.player)
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (!withFriends) {
            PlayerNameItem(
                name = players.firstOrNull().orEmpty(),
                onNameChange = { _, name -> onAction(MenuUiAction.PlayerNameChanged(name = name)) },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(items = players, key = { i, _ -> i }) { index, name ->
                    PlayerNameItem(
                        name = name,
                        onNameChange = { index, name ->
                            onAction(MenuUiAction.PlayerNameChanged(name = name, index = index))
                        },
                        index = index,
                        multiplayer = uiState.multiPlayer,
                        onDelePlayer = { onAction(MenuUiAction.RemovePlayerClicked(it)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    IconButton(
                        onClick = { onAction(MenuUiAction.AddNewPlayerClicked) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonAddAlt1,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerNameItem(
    name: String,
    onNameChange: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    index: Int = 0,
    multiplayer: Boolean = false,
    onDelePlayer: (Int) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { newValue -> onNameChange(index, newValue) },
            label = { Text("What's your name?") },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(fraction = if (multiplayer) 0.9f else 1f)
        )
        if (multiplayer) {
            IconButton(
                onClick = { onDelePlayer(index) },
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            }
        }
    }
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
            .padding(vertical = 16.dp)
    ) {
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
private fun MenuScreenPreview(
    @PreviewParameter(MenuUiStatePreviewProvider::class) uiState: MenuUiState
) {
    PokeGuessTheme {
        MenuContent(
            onState = uiState,
            onAction = {},
        )
    }
}
