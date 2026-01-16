package br.com.seucaio.pokeguess.data.local.source

import br.com.seucaio.pokeguess.data.local.database.dao.PokemonDao
import br.com.seucaio.pokeguess.data.local.database.entity.PokemonEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface PokemonLocalDataSource {
    suspend fun getAllPokemons(): List<PokemonEntity>
    suspend fun getAllByGeneration(offset: Int, limit: Int): List<PokemonEntity>
    suspend fun getByPokemonId(pokemonId: Int): PokemonEntity?
    suspend fun clearAndCachePokemons(pokemons: List<PokemonEntity>)
    suspend fun hasNoPokemons(): Boolean
    suspend fun hasPokemons(): Boolean
}

internal class PokemonLocalDataSourceImpl(
    private val pokemonDao: PokemonDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PokemonLocalDataSource {
    override suspend fun getAllPokemons(): List<PokemonEntity> {
        return withContext(ioDispatcher) { pokemonDao.getAll() }
    }

    override suspend fun getAllByGeneration(offset: Int, limit: Int): List<PokemonEntity> {
        return withContext(ioDispatcher) { pokemonDao.getByGeneration(offset, limit) }
    }

    override suspend fun getByPokemonId(pokemonId: Int): PokemonEntity? {
        return withContext(ioDispatcher) { pokemonDao.getById(pokemonId) }
    }

    override suspend fun clearAndCachePokemons(pokemons: List<PokemonEntity>) {
        withContext(ioDispatcher) {
            pokemonDao.deleteAll()
            pokemonDao.insertAll(pokemons)
        }
    }

    override suspend fun hasNoPokemons(): Boolean {
        return withContext(ioDispatcher) { pokemonDao.isEmpty() }
    }

    override suspend fun hasPokemons(): Boolean {
        return withContext(ioDispatcher) { pokemonDao.hasData() }
    }
}
