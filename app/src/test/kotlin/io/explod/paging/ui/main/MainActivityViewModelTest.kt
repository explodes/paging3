package io.explod.paging.ui.main

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.google.common.truth.Truth.assertThat
import io.explod.paging.R
import io.explod.paging.data.repo.GithubRepo
import io.explod.paging.testing.UnitTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class MainActivityViewModelTest : UnitTest() {

    @Inject
    internal lateinit var githubRepo: GithubRepo

    private lateinit var underTest: MainActivityViewModel

    override fun performInjection() {
        injector.inject(this)
    }

    @Before
    fun setUp() {
        underTest = MainActivityViewModel(githubRepo)
    }

    @Test
    fun `error append`() {
        val idle = LoadState.NotLoading(false)
        val error = LoadState.Error(Exception("test"))
        underTest.onLoadStates(
            CombinedLoadStates(
                refresh = idle,
                prepend = idle,
                append = error,
                source = LoadStates(refresh = idle, prepend = idle, append = idle),
                mediator = LoadStates(refresh = idle, prepend = idle, append = idle),
            )
        )

        assertThat(underTest.errorMessage.value?.messageRes).isEqualTo(R.string.error_message_unexpected)
    }

    @Test
    fun `error append rate limited`() {
        val idle = LoadState.NotLoading(false)
        val error = LoadState.Error(
            HttpException(
                Response.error<String>(
                    403,
                    "error".toResponseBody("text/plain".toMediaType())
                )
            )
        )
        underTest.onLoadStates(
            CombinedLoadStates(
                refresh = idle,
                prepend = idle,
                append = error,
                source = LoadStates(refresh = idle, prepend = idle, append = idle),
                mediator = LoadStates(refresh = idle, prepend = idle, append = idle),
            )
        )

        assertThat(underTest.errorMessage.value?.messageRes).isEqualTo(R.string.error_message_rate_limiting)
    }
}