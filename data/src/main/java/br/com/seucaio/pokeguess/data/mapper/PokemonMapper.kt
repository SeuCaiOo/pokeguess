package br.com.seucaio.pokeguess.data.mapper

import br.com.seucaio.pokeguess.data.remote.dto.PokemonListResponse
import br.com.seucaio.pokeguess.data.remote.dto.PokemonResponse
import br.com.seucaio.pokeguess.data.remote.service.PokemonApiService
import br.com.seucaio.pokeguess.domain.model.Pokemon

object PokemonMapper {
    fun PokemonListResponse.toPokemonDomainList(): List<Pokemon> {
        return results.map { it.toPokemonDomain() }
    }

    fun PokemonResponse.toPokemonDomain(): Pokemon {
        val id = url.split("/").dropLast(1).last().toInt()
        val artWorkImageUrl = "${PokemonApiService.ARTWORK_URL}/$id.png"
        return Pokemon(id = id, name = name, imageUrl = artWorkImageUrl)
    }
}