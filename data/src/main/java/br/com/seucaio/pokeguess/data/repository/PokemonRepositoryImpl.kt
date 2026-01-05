package br.com.seucaio.pokeguess.data.repository

import br.com.seucaio.pokeguess.data.mapper.PokemonMapper.toPokemonDomainList
import br.com.seucaio.pokeguess.data.remote.source.PokemonRemoteDataSource
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {
    override suspend fun getPokemons(generation: Generation): List<Pokemon> {
        return remoteDataSource.getPokemons(offset = generation.offset, limit = generation.limit)
            .toPokemonDomainList()
    }
}
