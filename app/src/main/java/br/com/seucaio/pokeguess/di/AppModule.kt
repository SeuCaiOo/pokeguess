package br.com.seucaio.pokeguess.di

import br.com.seucaio.pokeguess.data.di.dataModule
import br.com.seucaio.pokeguess.domain.di.domainModule
import br.com.seucaio.pokeguess.domain.usecase.CalculateGameStatsUseCase
import br.com.seucaio.pokeguess.domain.usecase.GetRandomPokemonUseCase
import br.com.seucaio.pokeguess.features.game.viewmodel.GameViewModel
import br.com.seucaio.pokeguess.features.home.viewmodel.HomeViewModel
import br.com.seucaio.pokeguess.features.menu.viewmodel.MenuViewModel
import br.com.seucaio.pokeguess.features.score.viewmodel.ScoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(domainModule)
    includes(dataModule)

    viewModel { HomeViewModel() }

    viewModel { params -> MenuViewModel(savedStateHandle = params.get()) }

    viewModel {
        GameViewModel(get<GetRandomPokemonUseCase>())
    }

    viewModel { params ->
        ScoreViewModel(
            savedStateHandle = params.get(),
            calculateGameStatsUseCase = get<CalculateGameStatsUseCase>(),
        )
    }
}
