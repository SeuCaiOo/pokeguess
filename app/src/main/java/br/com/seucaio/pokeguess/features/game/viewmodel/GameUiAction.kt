package br.com.seucaio.pokeguess.features.game.viewmodel

sealed interface GameUiAction {
    data class SubmitGuess(val guess: String) : GameUiAction
    data object NextPokemon : GameUiAction
    data object OnBackPressed : GameUiAction
    data class OnGuessChange(val guess: String) : GameUiAction
}
