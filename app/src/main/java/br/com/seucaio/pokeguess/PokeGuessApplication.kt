package br.com.seucaio.pokeguess

import android.app.Application
import br.com.seucaio.pokeguess.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class PokeGuessApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@PokeGuessApplication)
            modules(appModule)
        }
    }
}
