package io.explod.paging.testing.app

import io.explod.paging.app.App
import io.explod.paging.inject.AppComponent
import io.explod.paging.inject.AppModule
import io.explod.paging.testing.inject.DaggerUnitTestAppComponent

class UnitTestApp : App() {
    override fun getInjector(): AppComponent {
        return DaggerUnitTestAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}