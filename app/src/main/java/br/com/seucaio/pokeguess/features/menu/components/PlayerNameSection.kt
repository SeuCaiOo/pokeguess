package br.com.seucaio.pokeguess.features.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessButton
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.features.menu.preview.PlayerNameSectionPreviewProvider
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiAction
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState

@Composable
fun PlayerNameSection(
    uiState: MenuUiState,
    onAction: (MenuUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        val label = stringResource(R.string.players)
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PlayerListItem(
            onAction = onAction,
            uiState = uiState
        )
    }
}

@Composable
private fun PlayerListItem(
    onAction: (MenuUiAction) -> Unit,
    uiState: MenuUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items = uiState.players,
            key = { index, _ -> index }
        ) { i, name ->
            PlayerNameItem(
                name = name,
                onNameChange = { iName, name ->
                    onAction(MenuUiAction.PlayerNameChanged(name = name, index = iName))
                },
                index = i,
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

        item {
            PokeGuessButton(
                text = stringResource(R.string.confirm),
                color = MaterialTheme.colorScheme.secondary,
                enabled = uiState.confirmPlayers,
                onClick = { onAction(MenuUiAction.PlayersBottomSheetVisibilityChanged(false)) },
                modifier = Modifier.fillMaxWidth()
            )
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
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (name.isBlank()) focusRequester.requestFocus()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { newValue -> onNameChange(index, newValue) },
            label = { Text(stringResource(R.string.whats_your_name)) },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(fraction = if (multiplayer) 0.9f else 1f)
                .focusRequester(focusRequester)
        )
        if (multiplayer) {
            IconButton(
                onClick = { onDelePlayer(index) },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PlayerNameSectionPreview(
    @PreviewParameter(PlayerNameSectionPreviewProvider::class) uiState: MenuUiState
) {
    PokeGuessTheme {
        Surface {
            PlayerNameSection(
                uiState = uiState,
                onAction = {}
            )
        }
    }
}
