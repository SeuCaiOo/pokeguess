package br.com.seucaio.pokeguess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class GameSettings(
    val generation: Generation,
    val timerEnabled: Boolean,
    val rounds: Int,
    val players: List<Player> = emptyList(),
    val playerNames: List<String> = players.map { it.name },
    val difficulty: Difficulty
) : Parcelable {
    val selectedGeneration: Generation
        get() = Generation.getGeneration(generation.name)

    fun addPlayers(players: List<Player>): GameSettings {
        return copy(players = players)
    }
}
