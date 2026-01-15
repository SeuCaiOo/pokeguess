package br.com.seucaio.pokeguess.core.designsystem.ui.component

import android.R.attr.contentDescription
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.core.designsystem.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.model.PokemonFrameData
import br.com.seucaio.pokeguess.core.designsystem.ui.component.preview.PokemonFramePreviewProvider
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import coil.compose.AsyncImage

@Composable
fun PokemonFrame(
    frameData: PokemonFrameData,
    modifier: Modifier = Modifier,
) {
    with(frameData) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(24.dp))
                .background(pokemonFrameColor)
                .padding(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            ) {
                AsyncImage(
                    model = pokemonImageUrl,
                    contentDescription = "Pokémon Image",
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit,
                    colorFilter = if (unknownPokemon) ColorFilter.tint(Color.Black) else null,
                    placeholder = if (LocalInspectionMode.current) {
                        painterResource(R.drawable.pikachu)
                    } else {
                        null
                    }
                )
            }

            if (!unknownPokemon) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    text = pokemonName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PokemonFramePreview(
    @PreviewParameter(PokemonFramePreviewProvider::class) pokemon: PokemonFrameData
) {
    PokeGuessTheme {
        PokemonFrame(
            frameData = pokemon,
        )
    }
}
