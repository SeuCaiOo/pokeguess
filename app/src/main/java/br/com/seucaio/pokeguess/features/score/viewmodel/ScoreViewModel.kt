package br.com.seucaio.pokeguess.features.score.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.usecase.CalculateGameStatsUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetLastMatchUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetMatchByIdUseCase
import br.com.seucaio.pokeguess.navigation.PokeGuessRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ScoreViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val calculateGameStatsUseCase: CalculateGameStatsUseCase,
    private val getLastMatchUseCase: GetLastMatchUseCase,
    private val getMatchByIdUseCase: GetMatchByIdUseCase
) : ViewModel() {

    private val route = savedStateHandle.toRoute<PokeGuessRoute.Score>()
    private val isFirstLaunch = !savedStateHandle.contains(KEY_UI_STATE)

//    private val statsCalculated: GameStats =
//        calculateGameStatsUseCase(score = route.score, total = route.total)

    val uiState: StateFlow<ScoreUiState> = savedStateHandle.getStateFlow(
        key = KEY_UI_STATE,
        initialValue = ScoreUiState(
            withFriends = route.withFriends,
//            gameStatsUi = GameStatsUi.fromGameStats(statsCalculated)
        )
    )

    private val _uiEvent = MutableSharedFlow<ScoreUiEvent>()
    val uiEvent: SharedFlow<ScoreUiEvent> = _uiEvent.asSharedFlow()

    init {
        if (isFirstLaunch) {
            route.matchId?.let { getMatchById(it) } ?: run { loadLastMatch() }
        }
    }

    fun handleAction(action: ScoreUiAction) {
        when (action) {
            // TODO Play again deve reiniciar já nas configs atuais
            is ScoreUiAction.PlayAgainClicked -> navigateToPlayAgain()
            // Todo voltar para o menu deve reiniciar já nas configs atuais
            // TODO adicionar forma listar o historico
            is ScoreUiAction.BackToHomeClicked -> navigateToHome()
            is ScoreUiAction.BackButtonClicked -> navigateBack()
        }
    }

    private fun loadLastMatch() {
        viewModelScope.launch {
            saveUiStateHandle { setLoading() }
            getLastMatchUseCase().let { result ->
                result.onSuccess { gameMatch -> saveGameMatch(gameMatch) }
                result.onFailure { error -> saveUiStateHandle { setError(error) } }
            }
        }
    }

    private fun getMatchById(matchId: Int) {
        viewModelScope.launch {
            saveUiStateHandle { setLoading() }
            getMatchByIdUseCase(matchId).let { result ->
                result.onSuccess { gameMatch -> saveGameMatch(gameMatch) }
                result.onFailure { error -> saveUiStateHandle { setError(error) } }
            }
        }
    }

    private fun saveGameMatch(gameMatch: GameMatch) {
        gameMatch.scorePlayers.values.toList().map { score ->
            calculateGameStatsUseCase(score = score, total = gameMatch.totalRounds)
        }.also { playerStats -> saveUiStateHandle { setGameMatch(gameMatch, playerStats) } }
    }

    private fun navigateToPlayAgain() {
        viewModelScope.launch { _uiEvent.emit(ScoreUiEvent.NavigateToMenu(uiState.value.withFriends)) }
    }

    private fun navigateToHome() {
        viewModelScope.launch { _uiEvent.emit(ScoreUiEvent.NavigateToHome) }
    }

    private fun navigateBack() {
        viewModelScope.launch { _uiEvent.emit(ScoreUiEvent.NavigateToBack) }
    }

    private fun saveUiStateHandle(block: ScoreUiState.() -> ScoreUiState) {
        savedStateHandle[KEY_UI_STATE] = uiState.value.block()
    }

    companion object {
        private const val KEY_UI_STATE = "ui_state"
    }
}
