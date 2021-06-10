package io.explod.paging.data.model

import retrofit2.HttpException

sealed interface ApiError {

    val exception: Exception

    /** HTTP error of sorts (400, 500) etc. */
    data class HTTP(val status: Int, override val exception: HttpException) : ApiError

    /** General Exceptions, such as Parsing or IO errors. */
    data class General(override val exception: Exception) : ApiError
}