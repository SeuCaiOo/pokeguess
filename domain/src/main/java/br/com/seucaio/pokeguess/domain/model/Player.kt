package br.com.seucaio.pokeguess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val id: Int = 0,
    val name: String,
    val matchIds: List<Int> = emptyList()
) : Parcelable
