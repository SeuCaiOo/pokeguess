package br.com.seucaio.pokeguess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val generation: Generation,
    val timerEnabled: Boolean,
    val rounds: Int,
    val withFriends: Boolean,
    val players: List<String>
) : Parcelable
