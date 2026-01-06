package br.com.seucaio.pokeguess.features.menu

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.Generation

@Composable
fun MenuScreen(
    onNavigateToGame: (Generation, Boolean) -> Unit
) {
    var selectedGeneration by remember { mutableStateOf(Generation.I) }
    var timerEnabled by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "PokéGuess",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Generation Selection
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Generation", style = MaterialTheme.typography.labelLarge)
                    Text(text = selectedGeneration.displayName, style = MaterialTheme.typography.bodyLarge)
                }
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
                            selectedGeneration = generation
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Timer Toggle
        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Game Timer", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = if (timerEnabled) "10 seconds per round" else "No time limit",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Switch(
                    checked = timerEnabled,
                    onCheckedChange = { timerEnabled = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { onNavigateToGame(selectedGeneration, timerEnabled) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Start Game")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MenuScreenPreview() {
    PokeGuessTheme {
        Surface {
            MenuScreen(
                onNavigateToGame = { _, _ -> }
            )
        }
    }
}
