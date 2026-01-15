package br.com.seucaio.pokeguess.data.mapper

import br.com.seucaio.pokeguess.data.local.database.entity.PokemonEntity
import br.com.seucaio.pokeguess.data.remote.dto.PokemonListResponse
import br.com.seucaio.pokeguess.data.remote.dto.PokemonResponse
import br.com.seucaio.pokeguess.data.remote.service.PokemonApiService
import br.com.seucaio.pokeguess.domain.model.Pokemon

internal object PokemonMapper {
    fun PokemonListResponse.toPokemonDomainList(): List<Pokemon> {
        return results.map { it.toPokemonDomain() }
    }

    fun PokemonResponse.toPokemonDomain(): Pokemon {
        val id = url.split("/").dropLast(1).last().toInt()
        val artWorkImageUrl = "${PokemonApiService.ARTWORK_URL}/$id.png"
        return Pokemon(id = id, name = name, imageUrl = artWorkImageUrl)
    }

    fun PokemonListResponse.toEntityList(): List<PokemonEntity> {
        return results.map { it.toEntity() }
    }

    fun PokemonResponse.toEntity(): PokemonEntity {
        val id = url.split("/").dropLast(1).last().toInt()
        val artWorkImageUrl = "${PokemonApiService.ARTWORK_URL}/$id.png"
        return PokemonEntity(id = id, name = name, imageUrl = artWorkImageUrl)
    }

    fun List<Pokemon>.toEntityList(): List<PokemonEntity> {
        return map { it.toPokemonEntity() }
    }

    fun Pokemon.toPokemonEntity(): PokemonEntity {
        return PokemonEntity(id = id, name = name, imageUrl = imageUrl)
    }

    fun List<PokemonEntity>.toDomainList(): List<Pokemon> {
        return map { it.toPokemonDomain() }
    }

    fun PokemonEntity.toPokemonDomain(): Pokemon {
        return Pokemon(id = id, name = name, imageUrl = imageUrl)
    }
}
