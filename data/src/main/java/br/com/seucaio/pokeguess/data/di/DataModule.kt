package br.com.seucaio.pokeguess.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import br.com.seucaio.pokeguess.data.local.database.PokeGuessDatabase
import br.com.seucaio.pokeguess.data.local.database.dao.GameMatchDao
import br.com.seucaio.pokeguess.data.local.database.dao.GameMatchPlayerDao
import br.com.seucaio.pokeguess.data.local.database.dao.PlayerDao
import br.com.seucaio.pokeguess.data.local.database.dao.PokemonDao
import br.com.seucaio.pokeguess.data.local.database.dao.RoundDao
import br.com.seucaio.pokeguess.data.local.source.GameMatchLocalDataSource
import br.com.seucaio.pokeguess.data.local.source.GameMatchLocalDataSourceImpl
import br.com.seucaio.pokeguess.data.local.source.GameMatchPlayerLocalDataSource
import br.com.seucaio.pokeguess.data.local.source.GameMatchPlayerLocalDataSourceImpl
import br.com.seucaio.pokeguess.data.local.source.PlayerLocalDataSource
import br.com.seucaio.pokeguess.data.local.source.PlayerLocalDataSourceImpl
import br.com.seucaio.pokeguess.data.local.source.PokemonLocalDataSource
import br.com.seucaio.pokeguess.data.local.source.PokemonLocalDataSourceImpl
import br.com.seucaio.pokeguess.data.local.source.RoundLocalDataSource
import br.com.seucaio.pokeguess.data.local.source.RoundLocalDataSourceImpl
import br.com.seucaio.pokeguess.data.remote.service.PokemonApiService
import br.com.seucaio.pokeguess.data.remote.service.RetrofitConfig
import br.com.seucaio.pokeguess.data.remote.service.interceptor.NetworkInterceptor
import br.com.seucaio.pokeguess.data.remote.source.PokemonRemoteDataSource
import br.com.seucaio.pokeguess.data.remote.source.PokemonRemoteDataSourceImpl
import br.com.seucaio.pokeguess.data.repository.GameMatchRepositoryImpl
import br.com.seucaio.pokeguess.data.repository.GameSettingsRepositoryImpl
import br.com.seucaio.pokeguess.data.repository.PlayerRepositoryImpl
import br.com.seucaio.pokeguess.data.repository.PokemonRepositoryImpl
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository
import br.com.seucaio.pokeguess.domain.repository.GameSettingsRepository
import br.com.seucaio.pokeguess.domain.repository.PlayerRepository
import br.com.seucaio.pokeguess.domain.repository.PokemonRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    // region Network
    single<HttpLoggingInterceptor> { NetworkInterceptor.loggingInterceptor() }
    single<OkHttpClient> {
        RetrofitConfig.okHttpClient(interceptors = listOf(get<HttpLoggingInterceptor>()))
    }
    single<Retrofit> { RetrofitConfig.providesRetrofit(okHttpClient = get<OkHttpClient>()) }
    single<PokemonApiService> { get<Retrofit>().create(PokemonApiService::class.java) }
    // endregion

    // region Database
    single<PokeGuessDatabase> { PokeGuessDatabase.getDatabase(context = get()) }
    single<PokemonDao> { get<PokeGuessDatabase>().pokemonDao() }
    single<GameMatchDao> { get<PokeGuessDatabase>().gameMatchDao() }
    single<PlayerDao> { get<PokeGuessDatabase>().playerDao() }
    single<RoundDao> { get<PokeGuessDatabase>().roundDao() }
    single<GameMatchPlayerDao> { get<PokeGuessDatabase>().gameMatchPlayerDao() }
    // endregion

    // region DataStore
    single {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().preferencesDataStoreFile("game_settings") }
        )
    }
    // endregion

    // region Data Source
    single<PokemonRemoteDataSource> {
        PokemonRemoteDataSourceImpl(apiService = get<PokemonApiService>())
    }
    single<PokemonLocalDataSource> {
        PokemonLocalDataSourceImpl(pokemonDao = get<PokemonDao>())
    }
    single<GameMatchLocalDataSource> {
        GameMatchLocalDataSourceImpl(gameMatchDao = get<GameMatchDao>())
    }
    single<PlayerLocalDataSource> {
        PlayerLocalDataSourceImpl(playerDao = get<PlayerDao>())
    }
    single<RoundLocalDataSource> {
        RoundLocalDataSourceImpl(roundDao = get<RoundDao>())
    }
    single<GameMatchPlayerLocalDataSource> {
        GameMatchPlayerLocalDataSourceImpl(gameMatchPlayerDao = get<GameMatchPlayerDao>())
    }
    // endregion

    // region Repository
    single<PokemonRepository> {
        PokemonRepositoryImpl(
            remoteDataSource = get<PokemonRemoteDataSource>(),
            localDataSource = get<PokemonLocalDataSource>(),
        )
    }
    single<GameMatchRepository> {
        GameMatchRepositoryImpl(
            matchLocalDataSource = get<GameMatchLocalDataSource>(),
            matchPlayerLocalDataSource = get<GameMatchPlayerLocalDataSource>(),
            roundLocalDataSource = get<RoundLocalDataSource>()
        )
    }
    single<PlayerRepository> {
        PlayerRepositoryImpl(localDataSource = get<PlayerLocalDataSource>())
    }
    single<GameSettingsRepository> {
        GameSettingsRepositoryImpl(
            dataStore = get<DataStore<Preferences>>(),
            context = androidContext()
        )
    }
    // endregion
}
