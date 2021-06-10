package io.explod.paging.inject

import dagger.Module
import dagger.Provides
import io.explod.paging.data.api.ApiExecutor
import io.explod.paging.data.api.GithubApi

@Module
class ApiExecutorModule {
    @Provides
    fun githubApiExecutor(
        apiExecutorFactory: ApiExecutor.Factory,
        api: GithubApi,
    ): ApiExecutor<GithubApi> {
        return apiExecutorFactory.create(api)
    }
}