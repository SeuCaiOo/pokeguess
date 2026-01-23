package br.com.seucaio.pokeguess.data.repository

import br.com.seucaio.pokeguess.data.local.source.PokemonLocalDataSource
import br.com.seucaio.pokeguess.data.mapper.PokemonMapper.toDomainList
import br.com.seucaio.pokeguess.data.mapper.PokemonMapper.toEntityList
import br.com.seucaio.pokeguess.data.mapper.PokemonMapper.toPokemonDomain
import br.com.seucaio.pokeguess.data.mapper.PokemonMapper.toPokemonDomainList
import br.com.seucaio.pokeguess.data.remote.dto.PokemonListResponse
import br.com.seucaio.pokeguess.data.remote.source.PokemonRemoteDataSource
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.PokemonRepository

internal class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) : PokemonRepository {
    override suspend fun getPokemons(generation: Generation): List<Pokemon> {
        return getSuccessListPokemon(generation)
    }

    private suspend fun getSuccessListPokemon(generation: Generation): List<Pokemon> {
        if (localDataSource.hasPokemons()) return getLocalPokemons(generation)
        return getPokemonsByGeneration(generation = generation)
    }

    private suspend fun getLocalPokemons(generation: Generation): List<Pokemon> {
        return localDataSource
            .getAllByGeneration(offset = generation.offset, limit = generation.limit)
            .toDomainList()
    }

    private suspend fun getPokemonsByGeneration(generation: Generation): List<Pokemon> {
        saveCachePokemons(getRemotePokemons().toPokemonDomainList())
        return getLocalPokemons(generation)
    }

    private suspend fun getRemotePokemons(
        generation: Generation = Generation.ALL
    ): PokemonListResponse {
        return remoteDataSource.getPokemons(offset = generation.offset, limit = generation.limit)
    }

    private suspend fun saveCachePokemons(results: List<Pokemon>) {
        localDataSource.clearAndCachePokemons(results.toEntityList())
    }

    override suspend fun getPokemonById(pokemonId: Int): Pokemon? {
        return localDataSource.getByPokemonId(pokemonId)?.toPokemonDomain()
    }
}
