package br.com.seucaio.pokeguess.features.home.viewmodel

sealed interface HomeUiAction {
    data object PlaySelected : HomeUiAction
    data object HistorySelected : HomeUiAction
}
