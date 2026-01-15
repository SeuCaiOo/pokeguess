package br.com.seucaio.pokeguess.data.remote.service.interceptor

import br.com.seucaio.pokeguess.data.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

internal object NetworkInterceptor {
    fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}
