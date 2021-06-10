package io.explod.paging.testing.inject

import dagger.Module
import dagger.Provides
import io.explod.paging.data.ImageLoader
import io.explod.paging.data.api.GithubApi
import io.mockk.mockk

/**
 *  Prevent network access in tests by mocking image loading and API.
 *  In a real app, we'd have have a Fake implementation that loads a local image instead of doing nothing.
 */
@Module
class FakeNetworkModule {

    @Provides
    fun imageLoader(): ImageLoader = mockk(relaxed = true)

    @Provides
    fun showsApi(): GithubApi = mockk(relaxed = true)
}