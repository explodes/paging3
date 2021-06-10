package io.explod.paging.inject

import dagger.Component
import io.explod.paging.data.ImageLoader
import io.explod.paging.ui.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CoroModule::class,
        ImageLoaderModule::class,
        ApiExecutorModule::class,
        ApiModule::class,
        DatabaseModule::class,
    ],
)
interface AppComponent {

    fun imageLoader(): ImageLoader // Used by BindingComponent

    fun inject(target: MainActivity)

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}