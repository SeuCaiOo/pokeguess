package br.com.seucaio.pokeguess.domain.di

import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository
import br.com.seucaio.pokeguess.domain.repository.PokemonRepository
import br.com.seucaio.pokeguess.domain.usecase.AdvanceRoundUseCase
import br.com.seucaio.pokeguess.domain.usecase.CalculateGameStatsUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetAllMatchesUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetLastMatchUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetMatchByIdUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetNextPokemonUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetNextRoundUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetPokemonsUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetRandomPokemonUseCase
import br.com.seucaio.pokeguess.domain.usecase.ProcessGuessUseCase
import br.com.seucaio.pokeguess.domain.usecase.SaveUserGuessUseCase
import br.com.seucaio.pokeguess.domain.usecase.StartGameMatchUseCase
import br.com.seucaio.pokeguess.domain.usecase.StartTimerUseCase
import br.com.seucaio.pokeguess.domain.usecase.ValidateGuessUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetRandomPokemonUseCase(get()) }
    factory { CalculateGameStatsUseCase() }
    factory { StartTimerUseCase() }
    factory { ValidateGuessUseCase() }
    factory { ProcessGuessUseCase(get()) }
    factory { GetNextRoundUseCase() }
    factory { GetNextPokemonUseCase() }
    factory { GetPokemonsUseCase(get<PokemonRepository>()) }
    factory { StartGameMatchUseCase(get<GetPokemonsUseCase>(), get<GameMatchRepository>()) }
    factory { GetLastMatchUseCase(get<GameMatchRepository>(), get<PokemonRepository>()) }
    factory { SaveUserGuessUseCase(get<GameMatchRepository>()) }
    factory { AdvanceRoundUseCase(get<SaveUserGuessUseCase>()) }
    factory { GetAllMatchesUseCase(get()) }
    factory { GetMatchByIdUseCase(get<GameMatchRepository>(), get<PokemonRepository>()) }
}
