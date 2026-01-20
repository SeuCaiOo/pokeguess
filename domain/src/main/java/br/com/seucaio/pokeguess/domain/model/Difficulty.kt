package br.com.seucaio.pokeguess.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}
