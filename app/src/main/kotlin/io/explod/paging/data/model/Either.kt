package io.explod.paging.data.model

sealed interface Either<A, B> {

    data class First<A, B>(val value: A) : Either<A, B> {
        override fun <T> map(first: (A) -> T, second: (B) -> T): T {
            return first(value)
        }
    }

    data class Second<A, B>(val value: B) : Either<A, B> {
        override fun <T> map(first: (A) -> T, second: (B) -> T): T {
            return second(value)
        }
    }

    fun <T> map(first: (A) -> T, second: (B) -> T): T

    fun <T> mapFirst(first: (A) -> T): Either<T, B> {
        return map(
            first = { First(first(it)) },
            second = {
                @Suppress("UNCHECKED_CAST")
                this as Either<T, B>
            },
        )
    }

    fun <T> mapSecond(second: (B) -> T): Either<A, T> {
        return map(
            first = {
                @Suppress("UNCHECKED_CAST")
                this as Either<A, T>
            },
            second = { Second(second(it)) },
        )
    }

    fun onFirst(first: (A) -> Unit): Either<A, B> {
        return map(
            first = { first(it); this },
            second = {
                this
            },
        )
    }

    fun onSecond(second: (B) -> Unit): Either<A, B> {
        return map(
            first = {
                this
            },
            second = { second(it); this },
        )
    }

    companion object {
        inline fun <T> capture(block: () -> T): Either<T, Throwable> {
            return try {
                First(block())
            } catch (ex: Exception) {
                Second(ex)
            }
        }

        /** Get the value from an Either whose sides are the same covariant type. */
        fun <T> Either<out T, out T>.collapse(): T {
            return when (this) {
                is First -> this.value
                is Second -> this.value
            }
        }
    }
}