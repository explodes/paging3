package io.explod.paging.data.api

import com.google.common.truth.Truth.assertThat
import io.explod.paging.data.model.ApiError
import io.explod.paging.data.model.Either
import io.explod.paging.testing.UnitTest
import io.explod.paging.testing.helpers.TestCoro
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ApiExecutorTest : UnitTest() {

    @Inject
    internal lateinit var factory: ApiExecutor.Factory

    @Inject
    internal lateinit var coro: TestCoro

    lateinit var api: API

    lateinit var underTest: ApiExecutor<API>

    override fun performInjection() {
        injector.inject(this)
    }

    @Before
    fun setUp() {
        api = mockk()
        underTest = factory.create(api)
    }

    @Test
    fun `sync ok`() = coro.runBlockingTest {
        every { api.sync() } returns Result("ok")

        val result = underTest.execute { sync() }

        assertThat(result).isInstanceOf(Either.First::class.java)
    }

    @Test
    fun `sync general error`() = coro.runBlockingTest {
        val ex = Exception("whaaat")
        every { api.sync() } throws ex

        val result = underTest.execute { sync() }

        assertThat(result).isInstanceOf(Either.Second::class.java)
        val apiError = result as Either.Second<Result, ApiError>
        assertThat(apiError.value).isInstanceOf(ApiError.General::class.java)
        val error = apiError.value as ApiError.General
        assertThat(error.exception).isEqualTo(ex)
    }

    @Test
    fun `sync http error`() = coro.runBlockingTest {
        val ex =
            HttpException(
                Response.error<String>(
                    500,
                    "error".toResponseBody("text/plain".toMediaType())
                )
            )
        every { api.sync() } throws ex

        val result = underTest.execute { sync() }

        assertThat(result).isInstanceOf(Either.Second::class.java)
        val apiError = result as Either.Second<Result, ApiError>
        assertThat(apiError.value).isInstanceOf(ApiError.HTTP::class.java)
        val error = apiError.value as ApiError.HTTP
        assertThat(error.exception).isEqualTo(ex)
        assertThat(error.status).isEqualTo(500)
    }

    @Test
    fun `async ok`() = coro.runBlockingTest {
        coEvery { api.async() } returns Result("ok")

        val result = underTest.execute { async() }

        assertThat(result).isInstanceOf(Either.First::class.java)
    }

    @Test
    fun `async general error`() = coro.runBlockingTest {
        val ex = Exception("whaaat")
        coEvery { api.async() } throws ex

        val result = underTest.execute { async() }

        assertThat(result).isInstanceOf(Either.Second::class.java)
        val apiError = result as Either.Second<Result, ApiError>
        assertThat(apiError.value).isInstanceOf(ApiError.General::class.java)
        val error = apiError.value as ApiError.General
        assertThat(error.exception).isEqualTo(ex)
    }

    @Test
    fun `async http error`() = coro.runBlockingTest {
        val ex =
            HttpException(
                Response.error<String>(
                    500,
                    "error".toResponseBody("text/plain".toMediaType())
                )
            )
        coEvery { api.async() } throws ex

        val result = underTest.execute { async() }

        assertThat(result).isInstanceOf(Either.Second::class.java)
        val apiError = result as Either.Second<Result, ApiError>
        assertThat(apiError.value).isInstanceOf(ApiError.HTTP::class.java)
        val error = apiError.value as ApiError.HTTP
        assertThat(error.exception).isEqualTo(ex)
        assertThat(error.status).isEqualTo(500)
    }

    data class Result(val value: String)

    interface API {
        fun sync(): Result
        suspend fun async(): Result
    }
}