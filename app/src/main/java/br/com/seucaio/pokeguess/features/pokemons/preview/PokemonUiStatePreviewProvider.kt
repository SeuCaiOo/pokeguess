package br.com.seucaio.pokeguess.features.pokemons.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.features.pokemons.viewmodel.PokemonUiState

class PokemonUiStatePreviewProvider : PreviewParameterProvider<PokemonUiState> {
    override val values: Sequence<PokemonUiState> = sequenceOf(
        PokemonUiState(
            isLoading = false,
            pokemons = listOf(
                Pokemon(
                    id = 25,
                    name = "Pikachu",
                    imageUrl = "..."
                ),
                Pokemon(
                    id = 26,
                    name = "Raichu",
                    imageUrl = "..."
                ),
                Pokemon(
                    id = 27,
                    name = "Sandshrew",
                    imageUrl = "..."
                ),
                Pokemon(
                    id = 28,
                    name = "Sandslash",
                    imageUrl = "..."
                ),
            ),
        ),
        PokemonUiState(
            isLoading = true,
        ),
        PokemonUiState(
            isLoading = false,
            pokemons = listOf()
        ),
        PokemonUiState(
            isLoading = false,
            errorMessage = "Failed to load Pokémon",
        )
    )
}
