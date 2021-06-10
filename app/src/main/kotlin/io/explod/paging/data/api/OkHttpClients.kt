package io.explod.paging.data.api

import io.explod.paging.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import java.util.concurrent.TimeUnit


fun appOkHttpClient(plus: OkHttpClient.Builder.() -> Unit = {}): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY))
    }

    builder.plus()

    return builder.build()
}

fun staticHeaderInterceptor(headers: List<Pair<String, String>>): Interceptor {
    return Interceptor { chain ->
        var builder = chain.request().newBuilder()
        for ((key, value) in headers) {
            builder = builder.addHeader(key, value)
        }
        val request = builder.build()
        chain.proceed(request)
    }
}