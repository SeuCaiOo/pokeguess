package br.com.seucaio.pokeguess.features.pokemons.viewmodel

sealed interface PokemonUiEvent {
    data object NavigateToBack : PokemonUiEvent
}
