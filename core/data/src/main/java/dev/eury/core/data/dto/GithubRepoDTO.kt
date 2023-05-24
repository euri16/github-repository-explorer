package dev.eury.core.data.dto

import com.squareup.moshi.Json

data class GithubRepoDtoResponse(
    val items: List<GithubRepoDTO>
)

data class GithubRepoDTO(
    val id: Long,
    val name: String,
    @Json(name = "full_name")
    val fullName: String,
    val owner: OwnerDTO,
    @Json(name = "html_url")
    val htmlUrl: String,
    val description: String,
    val language: String?,
    @Json(name = "forks_count")
    val forksCount: Long,
    @Json(name = "stargazers_count")
    val stargazersCount: Long,
    @Json(name = "watchers_count")
    val watchersCount: Long,
    val topics: List<String>
)

data class OwnerDTO(
    val id: Long,
    @Json(name = "login")
    val name: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    val type: String
)