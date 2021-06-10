package io.explod.paging.inject

import dagger.Module
import dagger.Provides
import io.explod.paging.data.Coro
import io.explod.paging.data.CoroImpl

@Module
class CoroModule {
    @Provides
    fun coro(): Coro {
        return CoroImpl()
    }
}