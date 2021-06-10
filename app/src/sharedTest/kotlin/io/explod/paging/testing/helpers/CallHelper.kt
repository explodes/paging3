package io.explod.paging.testing.helpers

import okhttp3.Headers
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun <T> createCall(body: T, code: Int = 200, headers: Headers = Headers.headersOf()): Call<T> {
    return object : Call<T> {

        private var executed = false
        private var cancelled = false

        override fun clone(): Call<T> {
            return createCall(body, code = code, headers = headers)
        }

        override fun execute(): Response<T> {
            executed = true
            return Response.success(code, body)
        }

        override fun enqueue(callback: Callback<T>) {
            if (executed) {
                callback.onFailure(this, Exception("already executed"))
                return
            }
            if (cancelled) {
                callback.onFailure(this, Exception("already cancelled"))
                return
            }
            callback.onResponse(this, execute())
        }

        override fun isExecuted(): Boolean {
            return executed
        }

        override fun cancel() {
            cancelled = true
        }

        override fun isCanceled(): Boolean {
            return cancelled
        }

        override fun request(): Request {
            return Request.Builder().build()
        }

        override fun timeout(): Timeout {
            return Timeout.NONE
        }

    }

}