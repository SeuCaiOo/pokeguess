package br.com.seucaio.pokeguess.features.pokemons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessContainer
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessErrorContent
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessLoadingContent
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokemonFrame
import br.com.seucaio.pokeguess.core.designsystem.ui.component.model.PokemonFrameData
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.pokemons.preview.PokemonUiStatePreviewProvider
import br.com.seucaio.pokeguess.features.pokemons.viewmodel.PokemonUiAction
import br.com.seucaio.pokeguess.features.pokemons.viewmodel.PokemonUiState
import br.com.seucaio.pokeguess.features.pokemons.viewmodel.PokemonViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PokemonContent(
        uiState = uiState,
        onListPokemon = {
            viewModel.handleAction(PokemonUiAction.ListPokemonsByGeneration(uiState.generation))
        },
        modifier = modifier
    )
}

@Composable
fun PokemonContent(
    uiState: PokemonUiState,
    onListPokemon: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PokeGuessContainer(
        modifier = modifier,
        topContent = {
            Text(
                text = uiState.generation.displayName,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        },
        centerContent = {
            when {
                uiState.isLoading -> {
                    PokeGuessLoadingContent()
                }

                uiState.errorMessage != null -> {
                    PokeGuessErrorContent(
                        message = uiState.errorMessage,
                        onRetry = { onListPokemon() },
                    )
                }

                uiState.pokemons.isEmpty() -> {
                    PokeGuessErrorContent(message = stringResource(R.string.no_pokemons_found))
                }

                else -> {
                    PokemonList(uiState.pokemons)
                }
            }
        }
    )
}

@Composable
private fun PokemonList(
    pokemons: List<Pokemon>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = pokemons, key = { it.id }) { pokemon ->
            PokemonFrame(
                frameData = PokemonFrameData(
                    pokemonName = "#${pokemon.id} ${pokemon.name.replaceFirstChar { it.uppercase() }}",
                    pokemonImageUrl = pokemon.imageUrl,
                    unknownPokemon = false,
                    guessCorrectly = true
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PokemonScreenPreview(
    @PreviewParameter(PokemonUiStatePreviewProvider::class) uiState: PokemonUiState,
) {
    PokeGuessTheme {
        PokemonContent(
            uiState = uiState,
            onListPokemon = {},
        )
    }
}
