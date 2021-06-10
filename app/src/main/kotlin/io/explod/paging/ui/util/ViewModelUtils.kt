package io.explod.paging.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified VM : ViewModel> ViewModelStoreOwner.lazyViewModel(crossinline factory: () -> ViewModelProvider.Factory): Lazy<VM> {
    return lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, factory()).get(VM::class.java)
    }
}