package io.explod.paging.data.api

import io.explod.paging.data.Coro
import io.explod.paging.data.model.ApiError
import io.explod.paging.data.model.Either
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/** Calls API functions and captures errors. */
class ApiExecutor<T : Any> private constructor(private val coro: Coro, private val api: T) {

    /** Run an API Call and return a Response. Exceptions and non-200 status codes are transformed to ApiErrors. */
    suspend fun <R> call(apiCall: suspend T.() -> Call<R>): Either<Response<R>, ApiError> {
        return withContext(coro.io) {
            try {
                val response = api.apiCall().awaitResponse()
                if (!response.isSuccessful) {
                    Either.Second(wrapException(HttpException(response)))
                } else {
                    Either.First(response)
                }
            } catch (ex: Exception) {
                Either.Second(wrapException(ex))
            }
        }
    }

    /** Run an API function and return the result. Exceptions and non-200 status codes are transformed to ApiErrors. */
    suspend fun <R> execute(apiCall: suspend T.() -> R): Either<R, ApiError> {
        return withContext(coro.io) {
            try {
                Either.First(api.apiCall())
            } catch (ex: Exception) {
                Either.Second(wrapException(ex))
            }
        }
    }

    private fun wrapException(ex: Exception): ApiError {
        return when (ex) {
            // Maybe instead parse an error body.
            is HttpException -> ApiError.HTTP(ex.code(), ex)
            else -> ApiError.General(ex)
        }
    }

    @Singleton
    class Factory @Inject constructor(private val coroProvider: Provider<Coro>) {
        fun <T : Any> create(api: T): ApiExecutor<T> {
            return ApiExecutor(coro = coroProvider.get(), api = api)
        }
    }
}

fun <T> Either<T, ApiError>.onSuccess(onSuccess: (T) -> Unit): Either<T, ApiError> {
    return this.onFirst(onSuccess)
}

fun <T> Either<T, ApiError>.onError(onError: (ApiError) -> Unit): Either<T, ApiError> {
    return this.onSecond(onError)
}