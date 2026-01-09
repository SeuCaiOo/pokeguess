package br.com.seucaio.pokeguess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameRound(
    val pokemonName: String,
    val pokemonImageUrl: String,
    val userGuess: String,
    val isCorrect: Boolean
) : Parcelable
