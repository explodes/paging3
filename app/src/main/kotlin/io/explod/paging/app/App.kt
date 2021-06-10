package io.explod.paging.app

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.jakewharton.threetenabp.AndroidThreeTen
import io.explod.paging.inject.AppComponent
import io.explod.paging.inject.DaggerBindingComponent
import io.explod.paging.inject.InjectorInstance
import timber.log.Timber

abstract class App : Application() {

    abstract fun getInjector(): AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        prepareDagger()
        Timber.plant(Timber.DebugTree())
    }

    private fun prepareDagger() {
        val appComponent = getInjector()

        // Prepare a component for injectable BindingAdapters.
        val bindingComponent = DaggerBindingComponent.builder()
            .appComponent(appComponent)
            .build()
        DataBindingUtil.setDefaultComponent(bindingComponent);

        InjectorInstance.injector = appComponent
    }
}