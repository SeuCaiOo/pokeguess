package br.com.seucaio.pokeguess.domain.model

enum class AccuracyLevel {
    High, Medium, Low
}

data class GameStats(
    val score: Int,
    val total: Int,
    val accuracy: Int,
    val incorrect: Int,
    val accuracyLevel: AccuracyLevel
)
