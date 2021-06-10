package io.explod.paging.inject

import androidx.databinding.DataBindingComponent
import dagger.Component
import io.explod.paging.ui.util.AppBindingAdapter
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class DataBindingScope

@DataBindingScope
@Component(dependencies = [AppComponent::class])
interface BindingComponent : DataBindingComponent {
    override fun getAppBindingAdapter(): AppBindingAdapter
}