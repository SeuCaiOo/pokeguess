package br.com.seucaio.pokeguess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class GameRoundPlayer(
    val name: String = "",
    val score: Int = 0,
    val guess: String = "",
    val correctGuess: Boolean = false,
    val skippedGuess: Boolean = false,
    val submittedGuess: Boolean = false,
    val filledGuess: Boolean = false,
) : Parcelable {

    fun changeGuess(guess: String): GameRoundPlayer {
        return copy(guess = guess, filledGuess = false)
    }

    fun setGuess(guess: String): GameRoundPlayer {
        return copy(
            guess = guess,
            skippedGuess = guess.isBlank(),
            filledGuess = true,
        )
    }

    fun setSkipGuess(): GameRoundPlayer {
        return copy(
            guess = "",
            skippedGuess = true,
            filledGuess = true,
        )
    }

    fun sumScore(newScore: Int): GameRoundPlayer {
        return copy(score = this.score + newScore)
    }

    fun checkGuess(pokemonName: String): GameRoundPlayer {
        val correctGuess = guess.trim().equals(pokemonName, ignoreCase = true)
        return copy(
            correctGuess = correctGuess,
            submittedGuess = true,
            skippedGuess = guess.isBlank(),
        ).sumScore(if (correctGuess) 1 else 0)
    }

    fun resetRound(): GameRoundPlayer {
        return copy(
            guess = "",
            correctGuess = false,
            skippedGuess = false,
            submittedGuess = false,
            filledGuess = false,
        )
    }
}
