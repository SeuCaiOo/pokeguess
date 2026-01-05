package br.com.seucaio.pokeguess.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpriteResponse(
    @SerialName("other")
    val other: OtherSpritesResponse
)
