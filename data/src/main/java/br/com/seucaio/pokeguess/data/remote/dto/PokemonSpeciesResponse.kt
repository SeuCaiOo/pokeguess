package br.com.seucaio.pokeguess.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpeciesResponse(
    val varieties: List<PokemonVarietyResponse>
)
