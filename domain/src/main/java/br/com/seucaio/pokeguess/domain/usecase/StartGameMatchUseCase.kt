package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.model.Pokemon
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class StartGameMatchUseCase(
    private val getPokemonsUseCase: GetPokemonsUseCase,
    private val gameMatchRepository: GameMatchRepository
) {
    suspend operator fun invoke(
        totalRounds: Int,
        generation: Generation,
        players: List<String> = emptyList()
    ): Result<List<Pokemon>> {
        return runCatching {
            getPokemonsUseCase(generation).getOrThrow().let { pokemons ->
                pokemons.shuffled().take(totalRounds).also { matchPokemons ->
                    val matchPokemonsWithOption =
                        matchPokemons.map { matchPokemon ->
                            matchPokemon.setShuffledRandomNames(pokemons.map { it.name })
                        }

                    gameMatchRepository.saveMatch(
                        GameMatch(
                            totalRounds = totalRounds,
                            rounds = matchPokemonsWithOption.associate { it.id to "" },
                            roundsMultiplayer = matchPokemonsWithOption.associate { pokemon ->
                                pokemon.id to players.associateWith { "" }
                            },
                            players = players,
                            pokemonIds = matchPokemonsWithOption.map { it.id },
                            pokemonsWithOption = matchPokemonsWithOption
                                .associate { it.id to it.randomNames },
                            pokemons = matchPokemonsWithOption
                        )
                    )
                }
            }
        }
    }
}
