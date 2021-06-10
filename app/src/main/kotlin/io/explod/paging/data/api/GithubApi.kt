package io.explod.paging.data.api

import io.explod.paging.data.model.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

// Alternative: Use a Github API client library.
interface GithubApi {
    @GET
    fun getUsersPage(@Url url: String): Call<List<GithubUserResponse>>
}