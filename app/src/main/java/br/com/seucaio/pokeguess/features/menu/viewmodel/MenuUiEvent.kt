package br.com.seucaio.pokeguess.features.menu.viewmodel

sealed interface MenuUiEvent {
    data object NavigateToGame : MenuUiEvent
    data object NavigateToPokemons : MenuUiEvent
    data object NavigateToBack : MenuUiEvent
}
