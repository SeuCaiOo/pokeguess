package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.PokemonRepository

class GetRandomPokemonUseCase(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(generation: Generation): Result<Pokemon> {
        return try {
            val pokemons = pokemonRepository.getPokemons(generation)
            if (pokemons.isNotEmpty()) {
                Result.success(pokemons.random())
            } else {
                Result.failure(Exception("No Pokémon found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
