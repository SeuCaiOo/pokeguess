package br.com.seucaio.pokeguess.features.score.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.seucaio.pokeguess.domain.model.GameStats
import br.com.seucaio.pokeguess.domain.usecase.CalculateGameStatsUseCase
import br.com.seucaio.pokeguess.features.score.model.GameStatsUi
import br.com.seucaio.pokeguess.navigation.PokeGuessRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScoreViewModel(
    savedStateHandle: SavedStateHandle,
    calculateGameStatsUseCase: CalculateGameStatsUseCase
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

    fun handleAction(action: ScoreUiAction) {
        when (action) {
            is ScoreUiAction.PlayAgainClicked -> navigateToPlayAgain()
            is ScoreUiAction.BackToHomeClicked -> navigateToHome()
        }
    }

    private fun navigateToPlayAgain() {
        viewModelScope.launch { _uiEvent.emit(ScoreUiEvent.NavigateToMenu) }
    }

    private fun navigateToHome() {
        viewModelScope.launch { _uiEvent.emit(ScoreUiEvent.NavigateToHome) }
    }
}
