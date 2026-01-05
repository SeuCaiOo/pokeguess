package br.com.seucaio.pokeguess.data.remote.service

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT_SECONDS: Long = 30L

object RetrofitConfig {
    private const val JSON_MEDIA_TYPE = "application/json"

    @OptIn(ExperimentalSerializationApi::class)
    fun jsonConverterFactory(): Converter.Factory {
        val contentType = JSON_MEDIA_TYPE.toMediaType()

        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }
        return json.asConverterFactory(contentType)
    }

    fun okHttpClient(interceptors: List<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .apply { interceptors.forEach { addInterceptor(it) } }
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PokemonApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory())
            .build()
    }
}
