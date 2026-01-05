package br.com.seucaio.gamedex.remote.service.interceptor

import br.com.seucaio.gamedex.data.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object NetworkInterceptor {
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
