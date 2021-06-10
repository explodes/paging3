package io.explod.paging.util

import retrofit2.Response


private const val headerLink = "link"
private const val linkPattern = "<([^>]+)>; rel=\"(\\w+)\""

fun <T> Response<T>.readLink(name: String): String? {
    // Quick parsing of the "link" header which looks like this:
    // <https://api.github.com/users?per_page=20&since=70>; rel="next", <https://api.github.com/users{?since}>; rel="first"
    val linkHeader = this.headers()[headerLink] ?: return null
    for (match in linkPattern.toRegex().findAll(linkHeader)) {
        if (match.groupValues[2] == name) {
            return match.groupValues[1]
        }
    }
    return null
}