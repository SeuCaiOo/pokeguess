package br.com.seucaio.pokeguess.navigation

import br.com.seucaio.pokeguess.domain.model.GameSettings
import kotlinx.serialization.Serializable

@Serializable
sealed interface PokeGuessRoute {
    @Serializable
    data object Home : PokeGuessRoute

    @Serializable
    data object History : PokeGuessRoute

    @Serializable
    data class Pokemons(
        val generation: String
    ) : PokeGuessRoute

    @Serializable
    data class Menu(val withFriends: Boolean = false) : PokeGuessRoute

    @Serializable
    data class Game(val settings: GameSettings) : PokeGuessRoute

    @Serializable
    data class Score(
        val score: Int,
        val total: Int,
        val withFriends: Boolean,
        val matchId: Int? = null
    ) : PokeGuessRoute
}
