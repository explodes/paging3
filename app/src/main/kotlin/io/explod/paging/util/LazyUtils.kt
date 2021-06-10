package io.explod.paging.util


fun <T> lazyFast(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE, initializer)
}