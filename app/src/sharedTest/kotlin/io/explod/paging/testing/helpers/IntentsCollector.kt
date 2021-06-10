package io.explod.paging.testing.helpers

import android.content.Intent
import androidx.test.espresso.intent.Intents

/** Capable of fetching intents that have been executed, but only returning results not yet fetched. */
class IntentsCollector {

    private var ignoreIntentsCount = 0

    internal fun reset() {
        ignoreIntentsCount = 0
    }

    fun next(): List<Intent> {
        val intents = Intents.getIntents()
        val new = intents.subList(ignoreIntentsCount, intents.size)
        ignoreIntentsCount = intents.size
        return new
    }
}