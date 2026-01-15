package br.com.seucaio.pokeguess.core.designsystem.ui.component.model

import androidx.compose.ui.graphics.Color
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.GrayPokeQuiz
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.GreenPokeQuiz
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.RedPokeQuiz

data class PokemonFrameData(
    val pokemonName: String,
    val pokemonImageUrl: String,
    val unknownPokemon: Boolean,
    val pokemonType: String = "",
    val guessCorrectly: Boolean = unknownPokemon
) {
    val pokemonFrameColor: Color
        get() {
            return when {
                !unknownPokemon && !guessCorrectly -> RedPokeQuiz
                !unknownPokemon && guessCorrectly -> GreenPokeQuiz
                else -> GrayPokeQuiz
            }
        }
}
