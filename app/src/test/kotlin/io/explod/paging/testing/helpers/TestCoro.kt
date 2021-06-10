@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.explod.paging.testing.helpers

import io.explod.paging.data.Coro
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest

class TestCoro : Coro {

    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    val scope = TestCoroutineScope(dispatcher)

    override val ui: CoroutineDispatcher
        get() = dispatcher

    override val io: CoroutineDispatcher
        get() = dispatcher

    override val default: CoroutineDispatcher
        get() = dispatcher

    override val db: CoroutineDispatcher
        get() = dispatcher

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        return runBlockingTest(dispatcher, block)
    }

}