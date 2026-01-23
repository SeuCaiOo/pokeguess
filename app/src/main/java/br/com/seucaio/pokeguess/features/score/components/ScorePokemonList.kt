package br.com.seucaio.pokeguess.features.score.components

import android.util.Log.i
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import br.com.seucaio.pokeguess.R
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokemonFrame
import br.com.seucaio.pokeguess.core.designsystem.ui.component.model.PokemonFrameData
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.GreenPokeQuiz
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.RedPokeQuiz
import br.com.seucaio.pokeguess.features.game.components.PlayerNameTag
import br.com.seucaio.pokeguess.features.score.preview.ScorePokemonListPreviewProvider
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreUiState

@Composable
fun ScorePokemonList(
    uiState: ScoreUiState,
    modifier: Modifier = Modifier,
) {
//    val pokemonsWithGuesses = uiState.pokemonsWithGuesses


    val roundPlayers = uiState.roundPlayers
    val scorePlayers = uiState.scorePlayers
    val pokemons = uiState.pokemons


    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {

        itemsIndexed(
            items = roundPlayers.toList(),
            key = { _, item -> item.first }
        ) { index, item ->
            val pokemonId = item.first
            val pokemon = pokemons.find { it.id == pokemonId }
            val pokemonName = pokemon?.name.orEmpty()
            val pokemonImageUrl = pokemon?.imageUrl.orEmpty()
            val isCorrect = item.second.values.toList().getOrNull(index) == pokemonName
            val singlePlayer = item.second.size == 1


            Column(
                modifier = Modifier
                    .fillParentMaxWidth(fraction = 0.6f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface),
                verticalArrangement = Arrangement.Center
            ) {
                PokemonFrame(
                    frameData = PokemonFrameData(
                        pokemonName = pokemonName,
                        pokemonImageUrl = pokemonImageUrl,
                        unknownPokemon = false,
                        guessCorrectly = isCorrect
                    )
                )

                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    itemsIndexed(
                        items = item.second.toList(),
                        key = { _, item -> item }
                    ) { index, item ->
                        val playerName = item.first
                        val playerGuess = item.second
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        ) {
                            if (!singlePlayer) PlayerNameTag(playerName)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = playerGuess,
                                    style = MaterialTheme.typography.titleLarge,
                                )
                                Icon(
                                    imageVector = if (isCorrect) {
                                        Icons.Default.Check
                                    } else {
                                        Icons.Default.Clear
                                    },
                                    tint = if (isCorrect) GreenPokeQuiz else RedPokeQuiz,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

@PreviewLightDark
@Composable
private fun ScorePokemonListPreview(
    @PreviewParameter(ScorePokemonListPreviewProvider::class) uiState: ScoreUiState
) {
    PokeGuessTheme {
        Surface {
            ScorePokemonList(
                uiState = uiState
            )
        }
    }
}
