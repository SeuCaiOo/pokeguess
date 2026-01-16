package br.com.seucaio.pokeguess.features.pokemons.viewmodel

import br.com.seucaio.pokeguess.domain.model.Generation

sealed interface PokemonUiAction {
    data class ListPokemonsByGeneration(val generation: Generation) : PokemonUiAction
    data object BackButtonClicked : PokemonUiAction
}
