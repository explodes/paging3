package io.explod.paging.testing.rules

import androidx.test.espresso.intent.Intents
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class EspressoIntentsRule : TestRule {
    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                Intents.init()
                try {
                    base.evaluate() // This will run the test.
                } finally {
                    Intents.release()
                }
            }
        }
    }
}