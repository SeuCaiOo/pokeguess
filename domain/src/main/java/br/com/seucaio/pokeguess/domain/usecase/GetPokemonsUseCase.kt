package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.PokemonRepository

class GetPokemonsUseCase(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(generation: Generation): Result<List<Pokemon>> {
        return runCatching {
            pokemonRepository.getPokemons(generation).ifEmpty {
                throw NoSuchElementException("No Pokémons found")
            }
        }
    }
}
