package br.com.seucaio.pokeguess.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val name: String,
    val url: String
)
