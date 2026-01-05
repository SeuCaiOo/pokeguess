package br.com.seucaio.pokeguess.data.remote.service

import br.com.seucaio.pokeguess.data.remote.dto.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
        private const val SPRITE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon"
        const val ARTWORK_URL = "$SPRITE_URL/other/official-artwork"
    }
}
