package io.explod.paging.testing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import io.explod.paging.inject.InjectorInstance
import io.explod.paging.testing.app.UnitTestApp
import io.explod.paging.testing.inject.UnitTestAppComponent
import io.explod.paging.testing.rules.TimberLoggingRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

abstract class UnitTest : BaseTest() {

    val app: UnitTestApp
        get() = ApplicationProvider.getApplicationContext() as UnitTestApp

    val injector: UnitTestAppComponent
        get() = InjectorInstance.injector as UnitTestAppComponent

    final override fun customRules(): TestRule {
        return RuleChain.emptyRuleChain()
            .around(InstantTaskExecutorRule())
            .around(TimberLoggingRule())
    }

}