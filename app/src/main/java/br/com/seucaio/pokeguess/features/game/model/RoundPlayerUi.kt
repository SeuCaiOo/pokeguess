package br.com.seucaio.pokeguess.features.game.model

import android.os.Parcelable
import br.com.seucaio.pokeguess.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoundPlayerUi(
    val name: String = "",
    val score: Int = 0,
    val guess: String = "",
    val correctGuess: Boolean = false,
    val skippedGuess: Boolean = false,
    val submittedGuess: Boolean = false,
    val filledGuess: Boolean = false,
    val showGuessBottomSheet: Boolean = false,
    val selected: Boolean = false
) : Parcelable {
    val buttonBottomSheetRes get() = if (guess.isNotBlank()) R.string.confirm else R.string.skip

    fun setGuess(guess: String): RoundPlayerUi {
        return copy(
            guess = guess,
            skippedGuess = guess.isBlank(),
            filledGuess = true,
            showGuessBottomSheet = false
        )
    }

    fun select(): RoundPlayerUi {
        return copy(selected = true, showGuessBottomSheet = true)
    }

    fun unselect(): RoundPlayerUi {
        return copy(selected = false, showGuessBottomSheet = false)
    }

    fun sumScore(score: Int): RoundPlayerUi {
        return copy(score = this.score + score)
    }

    fun resetRound(): RoundPlayerUi {
        return copy(
            guess = "",
            correctGuess = false,
            skippedGuess = false,
            submittedGuess = false,
            filledGuess = false,
            showGuessBottomSheet = false,
            selected = false
        )
    }
}
