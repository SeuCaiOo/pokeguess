package br.com.seucaio.pokeguess.features.menu.viewmodel

import android.os.Parcelable
import br.com.seucaio.pokeguess.features.menu.model.SettingsUi
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuUiState(
    val settingsUi: SettingsUi = SettingsUi(),
) : Parcelable {
    val selectedGeneration get() = settingsUi.generation
    val timerEnabled get() = settingsUi.timerEnabled
    val rounds get() = settingsUi.rounds
    val withFriends get() = settingsUi.withFriends
    val playerName get() = settingsUi.playerName
    val startGameIsAvailable get() = settingsUi.nameFilled && settingsUi.roundsFilled

    fun updateSettings(settingsUi: SettingsUi): MenuUiState = copy(settingsUi = settingsUi)
}
