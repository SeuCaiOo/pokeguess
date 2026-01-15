package br.com.seucaio.pokeguess.data.local.source

import br.com.seucaio.pokeguess.data.local.database.dao.PokemonDao
import br.com.seucaio.pokeguess.data.local.database.entity.PokemonEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface PokemonLocalDataSource {
    suspend fun getAllPokemons(): List<PokemonEntity>
    suspend fun getByPokemonId(pokemonId: Int): PokemonEntity?
    suspend fun clearAndCachePokemons(pokemons: List<PokemonEntity>)
}

internal class PokemonLocalDataSourceImpl(
    private val pokemonDao: PokemonDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PokemonLocalDataSource {
    override suspend fun getAllPokemons(): List<PokemonEntity> {
        return withContext(ioDispatcher) { pokemonDao.getAll() }
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
}
