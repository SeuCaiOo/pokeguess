package br.com.seucaio.pokeguess.features.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.SettingsItemColumn
import br.com.seucaio.pokeguess.core.designsystem.ui.component.SettingsItemRow
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.Difficulty
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.features.menu.model.DifficultyUi
import br.com.seucaio.pokeguess.features.menu.preview.SettingsSectionPreviewProvider
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState

@Composable
fun SettingsSection(
    menuState: MenuUiState,
    modifier: Modifier = Modifier,
    onGenerationSelect: (Generation) -> Unit = {},
    onDifficultySelect: (Difficulty) -> Unit = {},
    onTimerToggle: (Boolean) -> Unit = {},
    onRoundsChange: (Int) -> Unit = {},
    onBottomSheetVisibilityChange: (Boolean) -> Unit = {}
) {
    val latestOnRoundsChange by rememberUpdatedState(onRoundsChange)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        DifficultySelector(
            selectedDifficulty = menuState.difficulty,
            onDifficultySelect = onDifficultySelect
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

        Spacer(modifier = Modifier.height(16.dp))
        PlayerName(
            players = menuState.players,
            onBottomSheetVisibilityChange = onBottomSheetVisibilityChange
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
        SettingsItemRow(
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
private fun DifficultySelector(
    selectedDifficulty: Difficulty,
    onDifficultySelect: (Difficulty) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsItemColumn(
        title = stringResource(R.string.difficulty),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Difficulty.entries.forEach { difficulty ->
                val selected = difficulty == selectedDifficulty
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = selected,
                            onClick = { onDifficultySelect(difficulty) },
                            role = Role.RadioButton
                        ),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = if (selected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = selected,
                            onClick = null
                        )
                        Text(
                            text = stringResource(DifficultyUi.getTextRes(difficulty)),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
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
    SettingsItemRow(
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
    SettingsItemRow(
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

@Composable
fun PlayerName(
    players: List<String>,
    onBottomSheetVisibilityChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsItemRow(
        modifier = modifier,
        title = stringResource(R.string.players),
        description = players.filter { it.isNotBlank() }.joinToString(", ")
            .ifBlank { stringResource(R.string.insert_player_name) },
        onClick = { onBottomSheetVisibilityChange(true) }
    ) {
        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
    }
}

@PreviewLightDark
@Composable
private fun SettingsSectionPreview(
    @PreviewParameter(SettingsSectionPreviewProvider::class) uiState: MenuUiState
) {
    PokeGuessTheme {
        Surface {
            SettingsSection(
                menuState = uiState
            )
        }
    }
}
