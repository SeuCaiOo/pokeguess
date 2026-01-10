package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.Pokemon

class GetNextPokemonUseCase {
    operator fun invoke(pokemons: List<Pokemon>, currentRound: Int): Result<Pokemon> {
        return runCatching {
            pokemons.getOrNull(currentRound)
                ?: throw NoSuchElementException("No next Pokémon found")
        }
    }
}
