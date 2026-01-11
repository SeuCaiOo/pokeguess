package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository
import br.com.seucaio.pokeguess.domain.repository.PokemonRepository

class GetMatchByIdUseCase(
    private val gameMatchRepository: GameMatchRepository,
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(matchId: Int): Result<GameMatch> {
        return runCatching {
            gameMatchRepository.getMatchById(matchId)?.let { gameMatch ->
                val pokemons = mutableListOf<Pokemon>()
                gameMatch.rounds.keys.forEach { pokemonId ->
                    pokemonRepository.getPokemonById(pokemonId)?.let {
                        pokemons.add(it)
                    }
                }
                gameMatch.setPokemons(pokemons)
            } ?: throw NoSuchElementException("No match by id found")
        }
    }
}
