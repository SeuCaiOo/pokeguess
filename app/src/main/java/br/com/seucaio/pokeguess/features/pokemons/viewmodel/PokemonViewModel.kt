package br.com.seucaio.pokeguess.features.pokemons.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.usecase.GetPokemonsUseCase
import br.com.seucaio.pokeguess.navigation.PokeGuessRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    private val route = savedStateHandle.toRoute<PokeGuessRoute.Pokemons>()
    private val currentState get() = uiState.value
    private val currentGeneration: Generation = Generation.valueOf(route.generation)

    val uiState: StateFlow<PokemonUiState> = savedStateHandle.getStateFlow(
        key = KEY_UI_STATE,
        initialValue = PokemonUiState(generation = currentGeneration)
    )

    init {
        handleAction(PokemonUiAction.ListPokemonsByGeneration(currentGeneration))
    }

    fun handleAction(action: PokemonUiAction) {
        when (action) {
            is PokemonUiAction.ListPokemonsByGeneration -> loadPokemons()
        }
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            saveUiStateHandle { setLoading() }
            getPokemonsUseCase(currentState.generation).fold(
                onSuccess = { pokemons -> saveUiStateHandle { setPokemons(pokemons) } },
                onFailure = { error -> saveUiStateHandle { setError(error) } }
            )
        }
    }

    private fun saveUiStateHandle(block: PokemonUiState.() -> PokemonUiState) {
        savedStateHandle[KEY_UI_STATE] = uiState.value.block()
    }

    companion object {
        private const val KEY_UI_STATE = "ui_state"
    }
}
