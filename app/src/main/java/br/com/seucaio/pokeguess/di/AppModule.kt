package br.com.seucaio.pokeguess.di

import br.com.seucaio.pokeguess.data.di.dataModule
import br.com.seucaio.pokeguess.domain.di.domainModule
import br.com.seucaio.pokeguess.domain.usecase.GetRandomPokemonUseCase
import br.com.seucaio.pokeguess.features.game.GameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(domainModule)
    includes(dataModule)

    viewModel {
        GameViewModel(get<GetRandomPokemonUseCase>())
    }
}
