package br.com.seucaio.pokeguess.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiEvent: MutableSharedFlow<HomeUiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<HomeUiEvent> = _uiEvent.asSharedFlow()

    fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.SoloModeSelected -> navigateToSoloMode()
            is HomeUiAction.FriendsModeSelected -> navigateToMenuFriends()
            is HomeUiAction.HistorySelected -> navigateToHistory()
        }
    }

    private fun navigateToSoloMode() {
        viewModelScope.launch { _uiEvent.emit(HomeUiEvent.NavigateToSoloMode) }
    }

    private fun navigateToMenuFriends() {
        viewModelScope.launch { _uiEvent.emit(HomeUiEvent.NavigateToFriendsMode) }
    }

    private fun navigateToHistory() {
        viewModelScope.launch { _uiEvent.emit(HomeUiEvent.NavigateToHistory) }
    }
}
