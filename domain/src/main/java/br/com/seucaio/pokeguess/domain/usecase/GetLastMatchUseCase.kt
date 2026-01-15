package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository
import br.com.seucaio.pokeguess.domain.repository.PokemonRepository

class GetLastMatchUseCase(
    private val gameMatchRepository: GameMatchRepository,
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Result<GameMatch> {
        return runCatching {
            gameMatchRepository.getLastMatch()?.let { gameMatch ->
                val pokemons = mutableListOf<Pokemon>()
                gameMatch.rounds.keys.forEach { pokemonId ->
                    pokemonRepository.getPokemonById(pokemonId)?.let {
                        pokemons.add(it)
                    }
                }
                gameMatch.setPokemons(pokemons)
            } ?: throw NoSuchElementException("No last match found")
        }
    }
}
