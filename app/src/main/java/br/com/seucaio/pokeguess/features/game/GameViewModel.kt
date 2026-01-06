package br.com.seucaio.pokeguess.features.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.usecase.GetRandomPokemonUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(private val getRandomPokemonUseCase: GetRandomPokemonUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private var timerJob: Job? = null
    private var currentGeneration: Generation = Generation.I
    private var isTimerEnabled: Boolean = false

    fun startGame(generation: Generation, timerEnabled: Boolean) {
        currentGeneration = generation
        isTimerEnabled = timerEnabled
        _gameState.update { it.copy(isTimerEnabled = timerEnabled) }
        loadPokemon()
    }

    fun loadPokemon() {
        stopTimer()
        viewModelScope.launch {
            _uiState.value = GameUiState.Loading
            getRandomPokemonUseCase(currentGeneration).onSuccess { pokemon ->
                _uiState.value = GameUiState.Success(pokemon)
                if (isTimerEnabled) {
                    startTimer()
                }
            }.onFailure {
                _uiState.value = GameUiState.Error(it.message ?: "Unknown error")
            }
        }
    }

    private fun startTimer() {
        _gameState.update { it.copy(remainingTime = TIMER_START_VALUE) }
        timerJob = viewModelScope.launch {
            while (_gameState.value.remainingTime > 0) {
                delay(TIMER_DELAY_MS)
                _gameState.update { it.copy(remainingTime = it.remainingTime - 1) }
            }
            onTimeUp()
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    private fun onTimeUp() {
        checkGuess("") // Failed to guess in time
    }

    fun checkGuess(guess: String) {
        val currentState = _uiState.value
        if (currentState is GameUiState.Success) {
            val isCorrect = guess.trim().equals(currentState.pokemon.name, ignoreCase = true)

            _gameState.update { state ->
                val newScore = if (isCorrect) state.score + 1 else state.score
                val newRound = state.currentRound + 1
                val isGameOver = newRound > state.totalRounds

                state.copy(
                    score = newScore,
                    currentRound = newRound,
                    isGameOver = isGameOver
                )
            }

            if (!_gameState.value.isGameOver) {
                loadPokemon()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    companion object {
        private const val TIMER_START_VALUE = 10
        private const val TIMER_DELAY_MS = 1000L
    }
}

sealed class GameUiState {
    data object Loading : GameUiState()
    data class Success(val pokemon: Pokemon) : GameUiState()
    data class Error(val message: String) : GameUiState()
}

data class GameState(
    val score: Int = 0,
    val currentRound: Int = 1,
    val totalRounds: Int = 10,
    val isGameOver: Boolean = false,
    val remainingTime: Int = 0,
    val isTimerEnabled: Boolean = false
)
