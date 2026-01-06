package br.com.seucaio.pokeguess.domain.di

import br.com.seucaio.pokeguess.domain.usecase.GetRandomPokemonUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetRandomPokemonUseCase(get()) }
}
