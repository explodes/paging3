package io.explod.paging.testing.rules

import org.junit.rules.TestRule
import org.junit.runners.model.Statement


inline fun rule(
    crossinline before: () -> Unit = {},
    crossinline after: () -> Unit = {},
): TestRule {
    return TestRule { base, _ ->
        object : Statement() {
            override fun evaluate() {
                before()
                try {
                    base.evaluate() // This will run the test.
                } finally {
                    after()
                }
            }
        }
    }
}