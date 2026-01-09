package br.com.seucaio.pokeguess.features.menu.model

import android.os.Parcelable
import br.com.seucaio.pokeguess.domain.model.Generation
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingsUi(
    val generation: Generation = Generation.I,
    val timerEnabled: Boolean = false,
    val rounds: Int = 0,
    val withFriends: Boolean = false,
    val playerName: String = ""
) : Parcelable {
    val nameFilled get() = playerName.isNotBlank()
    val roundsFilled get() = rounds > 0

    fun setGeneration(generation: Generation): SettingsUi = copy(generation = generation)

    fun setTimer(enabled: Boolean): SettingsUi = copy(timerEnabled = enabled)

    fun setNumberRounds(rounds: Int): SettingsUi = copy(rounds = rounds)

    fun setName(name: String): SettingsUi = copy(playerName = name)

    fun setWithFriends(withFriends: Boolean): SettingsUi = copy(withFriends = withFriends)

}