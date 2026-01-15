package br.com.seucaio.pokeguess.features.menu.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.seucaio.pokeguess.features.menu.model.SettingsUi
import br.com.seucaio.pokeguess.navigation.PokeGuessRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<PokeGuessRoute.Menu>()
    private val currentState get() = uiState.value
    private val currentSettingsState get() = currentState.settingsUi

    val uiState: StateFlow<MenuUiState> = savedStateHandle.getStateFlow(
        key = KEY_UI_STATE,
        initialValue = MenuUiState(settingsUi = SettingsUi(withFriends = route.withFriends))
    )

    private val _uiEvent = MutableSharedFlow<MenuUiEvent>()
    val uiEvent: SharedFlow<MenuUiEvent> = _uiEvent

    fun handleAction(action: MenuUiAction) {
        when (action) {
            is MenuUiAction.StartGameClicked -> navigateToGame()
            is MenuUiAction.GenerationSelected -> saveUiStateHandle {
                updateSettings(currentSettingsState.setGeneration(action.generation))
            }

            is MenuUiAction.TimerToggled -> saveUiStateHandle {
                updateSettings(currentSettingsState.setTimer(action.enabled))
            }

            is MenuUiAction.NumberOfRoundsChanged -> saveUiStateHandle {
                updateSettings(currentSettingsState.setNumberRounds(action.rounds))
            }

            is MenuUiAction.PlayerNameChanged -> saveUiStateHandle {
                updateSettings(currentSettingsState.setName(action.name))
            }
        }
    }

    private fun navigateToGame() {
        viewModelScope.launch { _uiEvent.emit(MenuUiEvent.NavigateToGame) }
    }

    private fun saveUiStateHandle(block: MenuUiState.() -> MenuUiState) {
        savedStateHandle[KEY_UI_STATE] = uiState.value.block()
    }

    companion object {
        private const val KEY_UI_STATE = "ui_state"
    }
}
