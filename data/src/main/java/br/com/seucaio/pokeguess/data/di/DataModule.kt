package br.com.seucaio.pokeguess.data.di

import br.com.seucaio.pokeguess.data.local.database.PokeGuessDatabase
import br.com.seucaio.pokeguess.data.local.database.dao.GameMatchDao
import br.com.seucaio.pokeguess.data.local.database.dao.PokemonDao
import br.com.seucaio.pokeguess.data.local.source.GameMatchLocalDataSource
import br.com.seucaio.pokeguess.data.local.source.GameMatchLocalDataSourceImpl
import br.com.seucaio.pokeguess.data.local.source.PokemonLocalDataSource
import br.com.seucaio.pokeguess.data.local.source.PokemonLocalDataSourceImpl
import br.com.seucaio.pokeguess.data.remote.service.PokemonApiService
import br.com.seucaio.pokeguess.data.remote.service.RetrofitConfig
import br.com.seucaio.pokeguess.data.remote.service.interceptor.NetworkInterceptor
import br.com.seucaio.pokeguess.data.remote.source.PokemonRemoteDataSource
import br.com.seucaio.pokeguess.data.remote.source.PokemonRemoteDataSourceImpl
import br.com.seucaio.pokeguess.data.repository.GameMatchRepositoryImpl
import br.com.seucaio.pokeguess.data.repository.PokemonRepositoryImpl
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository
import br.com.seucaio.pokeguess.domain.repository.PokemonRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    // endregion

    single<PokemonRepository> {
        PokemonRepositoryImpl(
            remoteDataSource = get<PokemonRemoteDataSource>(),
            localDataSource = get<PokemonLocalDataSource>(),
        )
    }
    single<GameMatchRepository> {
        GameMatchRepositoryImpl(
            localDataSource = get<GameMatchLocalDataSource>()
        )
    }
}
