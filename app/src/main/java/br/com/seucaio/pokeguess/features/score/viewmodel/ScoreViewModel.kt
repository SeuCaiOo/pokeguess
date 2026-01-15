package br.com.seucaio.pokeguess.features.score.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.seucaio.pokeguess.domain.model.GameStats
import br.com.seucaio.pokeguess.domain.usecase.CalculateGameStatsUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetLastMatchUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetMatchByIdUseCase
import br.com.seucaio.pokeguess.features.score.model.GameStatsUi
import br.com.seucaio.pokeguess.navigation.PokeGuessRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScoreViewModel(
    savedStateHandle: SavedStateHandle,
    calculateGameStatsUseCase: CalculateGameStatsUseCase,
    private val getLastMatchUseCase: GetLastMatchUseCase,
    private val getMatchByIdUseCase: GetMatchByIdUseCase
) : ViewModel() {

    private val route = savedStateHandle.toRoute<PokeGuessRoute.Score>()

    private val statsCalculated: GameStats =
        calculateGameStatsUseCase(score = route.score, total = route.total)

    private val _uiState = MutableStateFlow(
        ScoreUiState(
            withFriends = route.withFriends,
            gameStatsUi = GameStatsUi.fromGameStats(statsCalculated)
        )
    )
    val uiState: StateFlow<ScoreUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ScoreUiEvent>()
    val uiEvent: SharedFlow<ScoreUiEvent> = _uiEvent.asSharedFlow()

    init {
        route.matchId?.let { getMatchById(it) } ?: run { loadLastMatch() }
    }

    fun handleAction(action: ScoreUiAction) {
        when (action) {
            is ScoreUiAction.PlayAgainClicked -> navigateToPlayAgain()
            is ScoreUiAction.BackToHomeClicked -> navigateToHome()
        }
    }

    private fun loadLastMatch() {
        viewModelScope.launch {
            _uiState.update { it.setLoading() }
            getLastMatchUseCase().let { result ->
                result.onSuccess { gameMatch -> _uiState.update { it.setGameMatch(gameMatch) } }
                result.onFailure { error -> _uiState.update { it.setError(error) } }
            }
        }
    }

    private fun getMatchById(matchId: Int) {
        viewModelScope.launch {
            _uiState.update { it.setLoading() }
            getMatchByIdUseCase(matchId).let { result ->
                result.onSuccess { gameMatch -> _uiState.update { it.setGameMatch(gameMatch) } }
                result.onFailure { error -> _uiState.update { it.setError(error) } }
            }
        }
    }

    private fun navigateToPlayAgain() {
        viewModelScope.launch { _uiEvent.emit(ScoreUiEvent.NavigateToMenu) }
    }

    private fun navigateToHome() {
        viewModelScope.launch { _uiEvent.emit(ScoreUiEvent.NavigateToHome) }
    }
}
