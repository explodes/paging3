package io.explod.paging.data.model

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("login")
    val username: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,
)