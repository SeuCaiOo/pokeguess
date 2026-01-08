package br.com.seucaio.pokeguess.features.home.viewmodel

sealed interface HomeUiAction {
    data object SoloModeSelected : HomeUiAction
    data object FriendsModeSelected : HomeUiAction
}
