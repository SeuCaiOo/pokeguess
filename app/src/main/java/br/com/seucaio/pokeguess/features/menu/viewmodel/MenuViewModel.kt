package br.com.seucaio.pokeguess.features.menu.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.seucaio.pokeguess.navigation.PokeGuessRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<PokeGuessRoute.Menu>()

    private val _uiState = MutableStateFlow(MenuUiState(withFriends = route.withFriends))
    val uiState: StateFlow<MenuUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<MenuUiEvent>()
    val uiEvent: SharedFlow<MenuUiEvent> = _uiEvent

    fun handleAction(action: MenuUiAction) {
        when (action) {
            is MenuUiAction.StartGameClicked -> navigateToGame()
            is MenuUiAction.GenerationSelected -> {
                updateUiState { setGeneration(action.generation) }
            }

            is MenuUiAction.TimerToggled -> {
                updateUiState { setTimer(action.enabled) }
            }

            is MenuUiAction.NumberOfRoundsChanged -> {
                updateUiState { setNumberRounds(action.rounds) }
            }
        }
    }

    private fun updateUiState(block: MenuUiState.() -> MenuUiState) {
        _uiState.update { it.block() }
    }

    private fun navigateToGame() {
        viewModelScope.launch { _uiEvent.emit(MenuUiEvent.NavigateToGame) }
    }
}
