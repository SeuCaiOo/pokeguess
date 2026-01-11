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
        playerName: String? = null,
    ): Result<List<Pokemon>> {
        return runCatching {
            getPokemonsUseCase(generation).getOrThrow().let { pokemons ->
                pokemons.shuffled().take(totalRounds).also { matchPokemons ->
                    gameMatchRepository.saveMatch(
                        GameMatch(
                            playerName = playerName,
                            totalRounds = totalRounds,
                            rounds = matchPokemons.associate { it.id to "" },
                        )
                    )
                }
            }
        }
    }
}
