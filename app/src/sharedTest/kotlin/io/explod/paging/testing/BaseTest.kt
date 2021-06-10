package io.explod.paging.testing

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.explod.paging.testing.helpers.IntentsCollector
import io.explod.paging.testing.rules.EspressoIntentsRule
import io.explod.paging.testing.rules.rule
import org.junit.After
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
abstract class BaseTest {

    protected val intents = IntentsCollector()

    protected open fun performInjection() {}

    protected abstract fun customRules(): TestRule

    @get:Rule
    val testRule: TestRule = RuleChain.emptyRuleChain()
        .around(EspressoIntentsRule())
        .around(rule(before = { performInjection() }))
        .around(CustomRuleExecutor())


    @After
    fun resetIntents() {
        intents.reset()
    }

    private inner class CustomRuleExecutor : TestRule {
        override fun apply(base: Statement?, description: Description?): Statement {
            return object : Statement() {
                override fun evaluate() {
                    customRules().apply(base, description).evaluate()
                }
            }
        }
    }
}