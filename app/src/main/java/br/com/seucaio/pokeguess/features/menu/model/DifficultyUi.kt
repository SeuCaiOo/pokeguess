package br.com.seucaio.pokeguess.features.menu.model

import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.domain.model.Difficulty

data object DifficultyUi {
    fun getTextRes(difficulty: Difficulty): Int {
        return when (difficulty) {
            Difficulty.EASY -> R.string.easy
            Difficulty.MEDIUM -> R.string.medium
            Difficulty.HARD -> R.string.hard
        }
    }
}
