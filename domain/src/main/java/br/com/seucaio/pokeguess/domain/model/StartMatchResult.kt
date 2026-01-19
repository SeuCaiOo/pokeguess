package br.com.seucaio.pokeguess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StartMatchResult(
    val gameId: Int,
    val pokemons: List<Pokemon>
) : Parcelable
