package br.com.seucaio.pokeguess.domain.repository

import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemons(generation: Generation): List<Pokemon>
}
