package br.com.seucaio.pokeguess.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class Difficulty {
    EASY,
    MEDIUM,
    HARD;

    companion object {
        fun getDifficulty(value: String?): Difficulty {
            return if (value.isNullOrEmpty()) Difficulty.EASY else Difficulty.valueOf(value)
        }
    }
}
