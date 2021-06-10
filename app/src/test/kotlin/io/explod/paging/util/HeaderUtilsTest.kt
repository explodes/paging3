package io.explod.paging.util

import com.google.common.truth.Truth.assertThat
import okhttp3.Headers
import org.junit.Test
import retrofit2.Response

class HeaderUtilsTest {

    @Test
    fun `readLink ok`() {
        val response =
            makeResponse("<https://api.github.com/users?per_page=20&since=70>; rel=\"next\", <https://api.github.com/users{?since}>; rel=\"first\"")

        assertThat(response.readLink("next")).isEqualTo("https://api.github.com/users?per_page=20&since=70")
        assertThat(response.readLink("first")).isEqualTo("https://api.github.com/users{?since}")
        assertThat(response.readLink("fictional")).isNull()
    }

    @Test
    fun `readLink empty`() {
        val response = makeResponse("")
        assertThat(response.readLink("fictional")).isNull()
    }

    @Test
    fun `readLink missing`() {
        val response = Response.success("body")
        assertThat(response.readLink("fictional")).isNull()
    }

    private fun makeResponse(link: String): Response<String> {
        return Response.success("body", Headers.headersOf("link", link))
    }
}