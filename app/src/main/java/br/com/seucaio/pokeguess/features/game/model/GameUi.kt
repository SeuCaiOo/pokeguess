package br.com.seucaio.pokeguess.features.game.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameUi(
    val score: Int = 0,
    val currentRound: Int = 0,
    val totalRounds: Int = 0,
    val isGameOver: Boolean = false,
    val remainingTime: Int = 0,
    val isTimerEnabled: Boolean = false,
    val correctGuess: Boolean = false,
    val guessSubmitted: Boolean = false
) : Parcelable {
    val progress: Float
        get() = currentRound.toFloat() / totalRounds.toFloat()

    val progressText: String
        get() = "${currentRound + 1}/$totalRounds"

    val scoreText: String
        get() = "Score: $score"

    val timeText: String
        get() = "Time: $remainingTime"

    fun updateTime(remainingTime: Int): GameUi {
        return copy(remainingTime = remainingTime)
    }

    fun checkGuess(score: Int, correctGuess: Boolean, guessSubmitted: Boolean): GameUi {
        return copy(score = score, correctGuess = correctGuess, guessSubmitted = guessSubmitted)
    }

    fun nextRound(
        currentRound: Int,
        isGameOver: Boolean,
        guessSubmitted: Boolean,
        correctGuess: Boolean,
        remainingTime: Int
    ): GameUi {
        return copy(
            currentRound = currentRound,
            isGameOver = isGameOver,
            guessSubmitted = guessSubmitted,
            correctGuess = correctGuess,
            remainingTime = remainingTime
        )
    }
}
