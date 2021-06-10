package io.explod.paging.testing.inject

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.explod.paging.data.Coro
import io.explod.paging.testing.helpers.TestCoro

@Module(includes = [UnitTestCoroModule.BindingModule::class])
class UnitTestCoroModule {

    @Provides
    fun testCoro(): TestCoro {
        return TestCoro()
    }

    @Module
    interface BindingModule {
        @Binds
        fun coro(impl: TestCoro): Coro
    }
}