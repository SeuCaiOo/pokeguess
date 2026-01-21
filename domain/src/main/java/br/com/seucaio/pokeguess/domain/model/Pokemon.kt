package br.com.seucaio.pokeguess.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

private const val RANDOM_NAMES_COUNT = 3

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val randomNames: List<String> = emptyList(),
) : Parcelable {
    fun setShuffledRandomNames(randomNames: List<String>): Pokemon {
        return randomNames.shuffled().take(RANDOM_NAMES_COUNT).plus(name).shuffled().let {
            this.copy(randomNames = it)
        }
    }
}
