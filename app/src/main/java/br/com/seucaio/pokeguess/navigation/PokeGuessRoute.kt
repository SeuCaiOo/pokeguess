package br.com.seucaio.pokeguess.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface PokeGuessRoute {
    @Serializable
    data object Home : PokeGuessRoute

    @Serializable
    data class Menu(val withFriends: Boolean) : PokeGuessRoute

    @Serializable
    data class Game(
        val generation: String,
        val timerEnabled: Boolean,
        val withFriends: Boolean = false
    ) : PokeGuessRoute

    @Serializable
    data class Score(
        val score: Int,
        val total: Int,
        val withFriends: Boolean
    ) : PokeGuessRoute
}
