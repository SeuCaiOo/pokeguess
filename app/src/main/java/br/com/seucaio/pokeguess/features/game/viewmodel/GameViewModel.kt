package br.com.seucaio.pokeguess.features.game.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.usecase.GetNextRoundUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetRandomPokemonUseCase
import br.com.seucaio.pokeguess.domain.usecase.ProcessGuessUseCase
import br.com.seucaio.pokeguess.domain.usecase.StartTimerUseCase
import br.com.seucaio.pokeguess.features.game.model.GameUi
import br.com.seucaio.pokeguess.navigation.PokeGuessRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GameViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getRandomPokemonUseCase: GetRandomPokemonUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val processGuessUseCase: ProcessGuessUseCase,
    private val getNextRoundUseCase: GetNextRoundUseCase
) : ViewModel() {

    private val route = savedStateHandle.toRoute<PokeGuessRoute.Game>()
    private val currentGeneration: Generation = Generation.valueOf(route.generation)
    private val currentState get() = uiState.value
    private val currentGameState get() = currentState.gameUi
    private val remainingTime = if (route.timerEnabled) TIMER_START_VALUE else 0

    val uiState: StateFlow<GameUiState> =
        savedStateHandle.getStateFlow(
            key = KEY_UI_STATE,
            initialValue = GameUiState(
                isLoading = true,
                withFriends = route.withFriends,
                gameUi = GameUi(
                    remainingTime = remainingTime,
                    isTimerEnabled = route.timerEnabled
                )
            )
        )

    private val _uiEvent = MutableSharedFlow<GameUiEvent>()
    val uiEvent: SharedFlow<GameUiEvent> = _uiEvent.asSharedFlow()

    private var timerJob: Job? = null

    init {
        // Load pokemon if not already loaded or if returning to active game
        if (currentState.pokemon == null && !currentGameState.isGameOver) loadPokemon()
    }

    fun handleAction(action: GameUiAction) {
        when (action) {
            is GameUiAction.SubmitGuess -> checkGuess(action.guess)
            is GameUiAction.NextPokemon -> onNextPokemon()
            is GameUiAction.OnBackPressed -> navigateBack()
            is GameUiAction.OnGuessChange -> {}
        }
    }

    private fun loadPokemon() {
        stopTimer()
        viewModelScope.launch {
            saveUiStateHandle { setLoading() }
            getRandomPokemonUseCase(currentGeneration)
                .onSuccess { pokemon ->
                    saveUiStateHandle { setSuccessPokemon(pokemon) }
                    if (currentState.gameTimerEnabled) startTimer()
                }
                .onFailure { error -> saveUiStateHandle { setError(error) } }
        }
    }

    private fun startTimer() {
        stopTimer()
        timerJob = startTimerUseCase(currentState.gameRemainingTime, TIMER_DELAY_MS)
            .onEach { time -> saveUiStateHandle { updateGameUi(gameUi.updateTime(time)) } }
            .onCompletion { cause ->
                val timeOut = cause == null && currentState.gameRemainingTime == 0
                if (timeOut) checkGuess("")
            }
            .launchIn(viewModelScope)
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    private fun checkGuess(guess: String) {
        currentState.pokemon?.let { pokemon ->
            stopTimer()
            processGuessUseCase(
                guess = guess,
                pokemonName = pokemon.name,
                currentScore = currentGameState.score
            ).let { result ->
                saveUiStateHandle {
                    checkGuess(
                        guess = guess,
                        gameUi = currentGameState.checkGuess(
                            score = result.newScore,
                            correctGuess = result.isCorrect,
                            guessSubmitted = true
                        )
                    )
                }
            }
        }
    }

    private fun onNextPokemon() {
        getNextRoundUseCase(
            currentRound = currentGameState.currentRound,
            totalRounds = currentGameState.totalRounds
        ).let { result ->
            val remainingTime = if (currentGameState.isTimerEnabled) TIMER_START_VALUE else 0
            saveUiStateHandle {
                nextRound(
                    gameUi = currentGameState.nextRound(
                        currentRound = result.nextRound,
                        isGameOver = result.isGameOver,
                        guessSubmitted = false,
                        correctGuess = false,
                        remainingTime = remainingTime
                    )
                )
            }

            if (result.isGameOver) navigateToScore() else loadPokemon()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch { _uiEvent.emit(GameUiEvent.NavigateBack) }
    }

    private fun navigateToScore() {
        viewModelScope.launch {
            _uiEvent.emit(
                GameUiEvent.GameOver(
                    score = currentGameState.score,
                    total = currentGameState.totalRounds,
                    withFriends = currentState.withFriends,
                )
            )
        }
    }

    private fun saveUiStateHandle(update: GameUiState.() -> GameUiState) {
        // TODO fix bug lost state guess + time
        savedStateHandle[KEY_UI_STATE] = uiState.value.update()
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    companion object {
        private const val KEY_UI_STATE = "ui_state"
        private const val TIMER_START_VALUE = 10
        private const val TIMER_DELAY_MS = 1000L
    }
}
