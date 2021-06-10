package io.explod.paging.util

import java.util.concurrent.ThreadFactory

fun namedThreadFactory(name: String): ThreadFactory {
    return ThreadFactory { r -> Thread(r, name) }
}