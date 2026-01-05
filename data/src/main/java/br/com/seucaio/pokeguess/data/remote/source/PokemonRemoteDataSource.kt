package br.com.seucaio.pokeguess.data.remote.source

import br.com.seucaio.pokeguess.data.remote.dto.PokemonListResponse
import br.com.seucaio.pokeguess.data.remote.service.PokemonApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface PokemonRemoteDataSource {
    suspend fun getPokemons(offset: Int, limit: Int): PokemonListResponse
}

class PokemonRemoteDataSourceImpl(
    private val apiService: PokemonApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PokemonRemoteDataSource {
    override suspend fun getPokemons(offset: Int, limit: Int): PokemonListResponse {
        return runCatching {
            withContext(ioDispatcher) { apiService.getPokemons(offset = offset, limit = limit) }
        }.getOrElse { PokemonListResponse(emptyList()) }
    }
}
