package br.com.seucaio.pokeguess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameMatch(
    val id: Int? = null,
    val playerName: String? = "Player #1",
    val totalRounds: Int,
    val score: Int? = null,
    val rounds: Map<Int, String> = emptyMap(),
    val createdAt: Long = System.currentTimeMillis(),
    val finishedAt: Long? = null
) : Parcelable
