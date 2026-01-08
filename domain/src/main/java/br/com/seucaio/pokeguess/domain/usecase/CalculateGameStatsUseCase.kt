package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.AccuracyLevel
import br.com.seucaio.pokeguess.domain.model.GameStats
import kotlin.math.roundToInt

private const val ACCURACY_PERCENTAGE = 100
private const val HIGH_ACCURACY_THRESHOLD = 80
private const val MEDIUM_ACCURACY_THRESHOLD = 50

class CalculateGameStatsUseCase {
    operator fun invoke(score: Int, total: Int): GameStats {
        val accuracy = if (total > 0) {
            (score.toFloat() / total * ACCURACY_PERCENTAGE).roundToInt()
        } else {
            0
        }
        val incorrect = total - score

        val accuracyLevel = when {
            accuracy >= HIGH_ACCURACY_THRESHOLD -> AccuracyLevel.High
            accuracy >= MEDIUM_ACCURACY_THRESHOLD -> AccuracyLevel.Medium
            else -> AccuracyLevel.Low
        }

        return GameStats(
            score = score,
            total = total,
            accuracy = accuracy,
            incorrect = incorrect,
            accuracyLevel = accuracyLevel
        )
    }
}
