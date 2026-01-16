package br.com.seucaio.pokeguess.features.pokemons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessErrorContent
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessLoadingContent
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessScaffold
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessTopAppBar
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
    onNavigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokemonViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val latestOnNavigateToBack by rememberUpdatedState(onNavigateToBack)

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                else -> latestOnNavigateToBack()
            }
        }
    }

    PokemonContent(
        uiState = uiState,
        uiAction = viewModel::handleAction,
        modifier = modifier
    )
}

@Composable
fun PokemonContent(
    uiState: PokemonUiState,
    uiAction: (PokemonUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    PokeGuessScaffold(
        modifier = modifier,
        topAppBar = {
            PokeGuessTopAppBar(
                title = uiState.generation.displayName,
                onBackButtonClick = { uiAction(PokemonUiAction.BackButtonClicked) }
            )
        },
        centerContent = {
            when {
                uiState.isLoading -> {
                    PokeGuessLoadingContent()
                }

                uiState.errorMessage != null -> {
                    PokeGuessErrorContent(
                        message = uiState.errorMessage,
                        onRetry = {
                            uiAction(PokemonUiAction.ListPokemonsByGeneration(uiState.generation))
                        },
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
            uiAction = {},
        )
    }
}
