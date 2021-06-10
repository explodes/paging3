package io.explod.paging.testing.inject

import dagger.Component
import io.explod.paging.inject.ApiExecutorModule
import io.explod.paging.inject.AppComponent
import io.explod.paging.inject.AppModule
import io.explod.paging.inject.CoroModule
import io.explod.paging.inject.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CoroModule::class,
        FakeNetworkModule::class,
        ApiExecutorModule::class,
        DatabaseModule::class,
    ],
)
interface AndroidTestAppComponent : AppComponent {
    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): AndroidTestAppComponent
    }
}