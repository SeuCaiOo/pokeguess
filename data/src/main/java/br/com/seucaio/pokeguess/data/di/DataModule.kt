package br.com.seucaio.pokeguess.data.di

import br.com.seucaio.pokeguess.data.remote.service.PokemonApiService
import br.com.seucaio.pokeguess.data.remote.service.RetrofitConfig
import br.com.seucaio.pokeguess.data.remote.service.interceptor.NetworkInterceptor
import br.com.seucaio.pokeguess.data.remote.source.PokemonRemoteDataSource
import br.com.seucaio.pokeguess.data.remote.source.PokemonRemoteDataSourceImpl
import br.com.seucaio.pokeguess.data.repository.PokemonRepositoryImpl
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

    // region Data Source
    single<PokemonRemoteDataSource> {
        PokemonRemoteDataSourceImpl(apiService = get<PokemonApiService>())
    }
    // endregion

    single<PokemonRepository> {
        PokemonRepositoryImpl(remoteDataSource = get<PokemonRemoteDataSource>())
    }
}
