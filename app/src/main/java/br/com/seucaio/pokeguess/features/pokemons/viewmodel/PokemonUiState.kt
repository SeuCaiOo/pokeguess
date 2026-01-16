package br.com.seucaio.pokeguess.features.pokemons.viewmodel

import android.os.Parcelable
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.model.Pokemon
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pokemons: List<Pokemon> = emptyList(),
    val generation: Generation = Generation.ALL
) : Parcelable {
    fun setLoading(isLoading: Boolean = true): PokemonUiState = copy(isLoading = isLoading)

    fun setError(error: Throwable): PokemonUiState {
        return copy(errorMessage = error.message, isLoading = false)
    }

    fun setPokemons(pokemons: List<Pokemon>): PokemonUiState {
        return copy(pokemons = pokemons, isLoading = false)
    }
}
