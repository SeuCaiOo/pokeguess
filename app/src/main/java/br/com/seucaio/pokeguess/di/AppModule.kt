package br.com.seucaio.pokeguess.di

import br.com.seucaio.pokeguess.data.di.dataModule
import br.com.seucaio.pokeguess.domain.di.domainModule
import org.koin.dsl.module

val appModule = module {
    includes(domainModule)
    includes(dataModule)
}
