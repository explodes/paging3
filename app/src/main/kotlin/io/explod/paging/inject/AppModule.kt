package io.explod.paging.inject

import dagger.Module
import dagger.Provides
import io.explod.paging.app.App

@Module
class AppModule(private val app: App) {

    @Provides
    fun app(): App = app

}