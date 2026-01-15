package br.com.seucaio.pokeguess.features.score.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.HighAccuracyColor
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.LowAccuracyColor
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.MediumAccuracyColor
import br.com.seucaio.pokeguess.domain.model.AccuracyLevel
import br.com.seucaio.pokeguess.domain.model.GameStats

data class GameStatsUi(
    val score: Int,
    val total: Int,
    val accuracy: Int,
    val incorrect: Int,
    private val accuracyLevel: AccuracyLevel
) {
    val accuracyColor: Color
        get() {
            return when (accuracyLevel) {
                AccuracyLevel.High -> HighAccuracyColor
                AccuracyLevel.Medium -> MediumAccuracyColor
                AccuracyLevel.Low -> LowAccuracyColor
            }
        }

    val accuracyValueRes: Int @StringRes get() = R.string.accuracy_value

    companion object {
        fun fromGameStats(gameStats: GameStats): GameStatsUi {
            return GameStatsUi(
                score = gameStats.score,
                total = gameStats.total,
                accuracy = gameStats.accuracy,
                incorrect = gameStats.incorrect,
                accuracyLevel = gameStats.accuracyLevel,
            )
        }

        fun default(): GameStatsUi = GameStatsUi(
            score = 0,
            total = 0,
            accuracy = 0,
            incorrect = 0,
            accuracyLevel = AccuracyLevel.Low
        )
    }
}
