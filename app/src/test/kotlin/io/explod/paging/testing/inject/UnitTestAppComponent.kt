package io.explod.paging.testing.inject

import dagger.Component
import io.explod.paging.UnitTestTest
import io.explod.paging.data.api.ApiExecutorTest
import io.explod.paging.inject.ApiExecutorModule
import io.explod.paging.inject.AppComponent
import io.explod.paging.inject.AppModule
import io.explod.paging.inject.DatabaseModule
import io.explod.paging.ui.main.MainActivityViewModelTest
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        UnitTestCoroModule::class,
        FakeNetworkModule::class,
        ApiExecutorModule::class,
        DatabaseModule::class,
    ],
)
interface UnitTestAppComponent : AppComponent {
    fun inject(target: UnitTestTest)
    fun inject(target: ApiExecutorTest)
    fun inject(target: MainActivityViewModelTest)

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): UnitTestAppComponent
    }
}