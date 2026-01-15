package br.com.seucaio.pokeguess.core.designsystem.ui.component.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.seucaio.pokeguess.core.designsystem.ui.component.model.PokemonFrameData

class PokemonFramePreviewProvider : PreviewParameterProvider<PokemonFrameData> {
    override val values: Sequence<PokemonFrameData> = sequenceOf(
        PokemonFrameData(
            pokemonName = "Pikachu",
            pokemonImageUrl = "...",
            unknownPokemon = true
        ),
        PokemonFrameData(
            pokemonName = "Pikachu",
            pokemonImageUrl = "...",
            unknownPokemon = false
        ),
        PokemonFrameData(
            pokemonName = "Pikachu",
            pokemonImageUrl = "...",
            unknownPokemon = false,
            pokemonType = "Electric",
            guessCorrectly = true

        ),
        PokemonFrameData(
            pokemonName = "Pikachu",
            pokemonImageUrl = "...",
            unknownPokemon = false,
            pokemonType = "Electric",
            guessCorrectly = false
        )
    )
}
