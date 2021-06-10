package io.explod.paging.data

import io.explod.paging.util.namedThreadFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/** We need an interface that provides dispatchers so that UnitTests can use fine-grained control over coroutines. */
interface Coro {
    val ui: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val db: CoroutineDispatcher
}

class CoroImpl : Coro {

    override val ui: CoroutineDispatcher
        get() = Dispatchers.Main

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    override val db: CoroutineDispatcher = Executors
        .newSingleThreadExecutor(namedThreadFactory("db"))
        .asCoroutineDispatcher()
}