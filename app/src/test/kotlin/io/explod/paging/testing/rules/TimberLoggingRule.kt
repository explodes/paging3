package io.explod.paging.testing.rules

import android.util.Log
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import timber.log.Timber

class TimberLoggingRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            private val tree = StdoutTree()
            override fun evaluate() {
                Timber.plant(tree)
                try {
                    base.evaluate()
                } finally {
                    Timber.uproot(tree)
                }
            }
        }
    }

    class StdoutTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (t == null) {
                println("${priorityMap[priority]}: $message")
            } else {
                println("${priorityMap[priority]}: $message: ${t.message}")
                t.printStackTrace(System.err)
            }
        }

        companion object {
            private val priorityMap = mapOf(
                Log.VERBOSE to "V",
                Log.DEBUG to "D",
                Log.INFO to "I",
                Log.WARN to "W",
                Log.ERROR to "E",
                Log.ASSERT to "A",
            )
        }
    }
}