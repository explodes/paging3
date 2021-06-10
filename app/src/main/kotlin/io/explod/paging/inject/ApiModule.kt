package io.explod.paging.inject

import dagger.Module
import dagger.Provides
import io.explod.paging.BuildConfig
import io.explod.paging.data.api.GithubApi
import io.explod.paging.data.api.appOkHttpClient
import io.explod.paging.data.api.staticHeaderInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    @Provides
    fun githubApi(): GithubApi {
        val headers = mutableListOf(
            headerUserAgent to BuildConfig.APP_USER_AGENT,
        )
        if (BuildConfig.GITHUB_CLIENT_TOKEN.isNotBlank()) {
            headers.add(headerAuth to (authToken + BuildConfig.GITHUB_CLIENT_TOKEN))
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(githubApiHost)
            .addConverterFactory(GsonConverterFactory.create())
            .client(appOkHttpClient {
                addInterceptor(staticHeaderInterceptor(headers))
            })
            .build()
        return retrofit.create(GithubApi::class.java)
    }

    companion object {
        private const val githubApiHost = "https://api.github.com"
        private const val headerUserAgent = "User-Agent"
        private const val headerAuth = "Authorization"
        private const val authToken = "token "
    }
}