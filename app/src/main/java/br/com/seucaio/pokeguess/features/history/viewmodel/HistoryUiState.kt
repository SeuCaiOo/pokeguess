package br.com.seucaio.pokeguess.features.history.viewmodel

import br.com.seucaio.pokeguess.domain.model.GameMatch

data class HistoryUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val matches: List<GameMatch> = emptyList()
) {
    val hasMatches = matches.isNotEmpty()

    fun setLoading(isLoading: Boolean = true): HistoryUiState {
        return copy(isLoading = isLoading)
    }

    fun setMatches(matches: List<GameMatch>): HistoryUiState {
        return copy(matches = matches, isLoading = false)
    }

    fun setError(error: Throwable): HistoryUiState {
        return copy(errorMessage = error.message, isLoading = false)
    }
}
