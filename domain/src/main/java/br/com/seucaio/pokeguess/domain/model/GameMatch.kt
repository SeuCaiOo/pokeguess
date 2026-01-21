package br.com.seucaio.pokeguess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameMatch(
    val id: Int? = null,
    val players: List<String> = emptyList(),
    val totalRounds: Int,
    val score: Int? = null,
    val rounds: Map<Int, String> = emptyMap(),
    val pokemons: List<Pokemon> = emptyList(),
    val pokemonIds: List<Int> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val finishedAt: Long? = null
) : Parcelable {
    val pokemonsWithGuesses: Map<Pokemon, String> get() {
        val pokemonsWithGuesses = mutableMapOf<Pokemon, String>()
        rounds.forEach { (pokemonId, guess) ->
            pokemons.find { it.id == pokemonId }?.let { pokemon ->
                pokemonsWithGuesses[pokemon] = guess
            }
        }
        return pokemonsWithGuesses
    }

    fun setPokemons(pokemons: List<Pokemon>) = copy(pokemons = pokemons)
}
