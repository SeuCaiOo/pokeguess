package br.com.seucaio.pokeguess.features.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.pokeguess.domain.usecase.GetAllMatchesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getAllMatchesUseCase: GetAllMatchesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HistoryUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        onAction(HistoryUiAction.LoadHistory)
    }

    fun onAction(action: HistoryUiAction) {
        when (action) {
            is HistoryUiAction.LoadHistory -> loadHistory()
            is HistoryUiAction.MatchClicked -> navigateToScoreByMatchId(action.matchId)
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _uiState.update { it.setLoading() }
            getAllMatchesUseCase()
                .onSuccess { matches ->
                    _uiState.update {
                        it.setMatches(
                            matches = matches.filter { match -> match.finishedAt != null }
                                .sortedByDescending { match -> match.finishedAt }
                        )
                    }
                }
                .onFailure { error -> _uiState.update { it.setError(error) } }
        }
    }

    private fun navigateToScoreByMatchId(matchId: Int) {
        viewModelScope.launch {
            _uiState.value.matches.firstOrNull { it.id == matchId }?.let {
                _uiEvent.emit(
                    HistoryUiEvent.NavigateToScoreByMatchId(
                        matchId = it.id ?: 0,
                        score = it.score ?: 0,
                        total = it.totalRounds,
                        withFriends = false
                    )
                )
            }
        }
    }
}
