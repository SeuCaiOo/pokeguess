package br.com.seucaio.pokeguess.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtherSpritesResponse(
    @SerialName("official-artwork")
    val officialArtwork: OfficialArtworkResponse
)
