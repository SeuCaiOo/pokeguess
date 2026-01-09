package br.com.seucaio.pokeguess.domain.di

import br.com.seucaio.pokeguess.domain.usecase.CalculateGameStatsUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetNextRoundUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetRandomPokemonUseCase
import br.com.seucaio.pokeguess.domain.usecase.ProcessGuessUseCase
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
}
