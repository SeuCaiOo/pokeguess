package br.com.seucaio.pokeguess.data.repository

import br.com.seucaio.pokeguess.data.local.database.entity.PokemonEntity
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
import kotlin.collections.isNotEmpty

internal class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) : PokemonRepository {
    override suspend fun getPokemons(generation: Generation): List<Pokemon> {
        return getSuccessListPokemon(generation)
    }

    private suspend fun getSuccessListPokemon(generation: Generation): List<Pokemon> {
        val localPokemons: List<PokemonEntity> = localDataSource.getAllPokemons()
        if (localPokemons.isNotEmpty()) return localPokemons.toDomainList()

        val remotePokemons: List<Pokemon> = getRemotePokemons(generation).toPokemonDomainList()
        saveCachePokemons(remotePokemons)

        return remotePokemons
    }

    private suspend fun getRemotePokemons(generation: Generation): PokemonListResponse {
        return remoteDataSource.getPokemons(offset = generation.offset, limit = generation.limit)
    }

    private suspend fun saveCachePokemons(results: List<Pokemon>) {
        localDataSource.clearAndCachePokemons(results.toEntityList())
    }

    override suspend fun getPokemonById(pokemonId: Int): Pokemon? {
        return localDataSource.getByPokemonId(pokemonId)?.toPokemonDomain()
    }
}
