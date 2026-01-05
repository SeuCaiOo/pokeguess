package br.com.seucaio.pokeguess.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfficialArtworkResponse(
    @SerialName("front_default")
    val frontDefault: String
)
