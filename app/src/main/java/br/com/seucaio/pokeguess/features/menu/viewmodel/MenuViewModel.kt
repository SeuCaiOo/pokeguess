package br.com.seucaio.pokeguess.features.menu.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.seucaio.pokeguess.domain.usecase.GetGameSettingsUseCase
import br.com.seucaio.pokeguess.domain.usecase.SaveGameSettingsUseCase
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuUiState.Companion.toGameSettings
import br.com.seucaio.pokeguess.navigation.PokeGuessRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MenuViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getGameSettingsUseCase: GetGameSettingsUseCase,
    private val saveGameSettingsUseCase: SaveGameSettingsUseCase,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<PokeGuessRoute.Menu>()
    private val isFirstLaunch = !savedStateHandle.contains(KEY_UI_STATE)
    private val currentState get() = uiState.value
    private val currentSettingsState get() = currentState.toGameSettings()

    val uiState: StateFlow<MenuUiState> = savedStateHandle.getStateFlow(
        key = KEY_UI_STATE,
        initialValue = MenuUiState()
    )

    private val _uiEvent = MutableSharedFlow<MenuUiEvent>()
    val uiEvent: SharedFlow<MenuUiEvent> = _uiEvent.asSharedFlow()

    init {
        if (isFirstLaunch) setSavedSettings()
    }

    fun handleAction(action: MenuUiAction) {
        when (action) {
            is MenuUiAction.StartGameClicked -> {
                saveSettings()
                navigateToGame()
            }

            is MenuUiAction.GenerationSelected -> saveUiStateHandle {
                setGeneration(action.generation)
            }

            is MenuUiAction.TimerToggled -> saveUiStateHandle {
                setTimer(action.enabled)
            }

            is MenuUiAction.NumberOfRoundsChanged -> saveUiStateHandle {
                setNumberRounds(action.rounds)
            }

            is MenuUiAction.PlayerNameChanged -> saveUiStateHandle {
                setPlayer(name = action.name, index = action.index)
            }

            is MenuUiAction.PokemonListClicked -> navigateToPokemons()

            is MenuUiAction.AddNewPlayerClicked -> saveUiStateHandle { addPlayer() }

            is MenuUiAction.RemovePlayerClicked -> saveUiStateHandle { removePlayer(action.index) }

            is MenuUiAction.BackButtonClicked -> navigateToBack()

            is MenuUiAction.PlayersBottomSheetVisibilityChanged -> saveUiStateHandle {
                setPlayersBottomSheetVisibility(action.visible)
            }
        }
    }

    private fun setSavedSettings() {
        viewModelScope.launch {
            val savedSettings = getGameSettingsUseCase().first()
            saveUiStateHandle { savedSettings.toMenuUiState() }
        }
    }

    private fun saveSettings() {
        viewModelScope.launch { saveGameSettingsUseCase(currentSettingsState) }
    }

    private fun navigateToGame() {
        viewModelScope.launch { _uiEvent.emit(MenuUiEvent.NavigateToGame) }
    }

    private fun navigateToPokemons() {
        viewModelScope.launch { _uiEvent.emit(MenuUiEvent.NavigateToPokemons) }
    }

    private fun navigateToBack() {
        viewModelScope.launch { _uiEvent.emit(MenuUiEvent.NavigateToBack) }
    }

    private fun saveUiStateHandle(block: MenuUiState.() -> MenuUiState) {
        savedStateHandle[KEY_UI_STATE] = uiState.value.block()
    }

    companion object {
        private const val KEY_UI_STATE = "ui_state"
    }
}
