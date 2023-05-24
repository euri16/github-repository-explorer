package dev.eury.core.models.repositories

data class GithubRepo(
    val id: Long = -1,
    val name: String = "",
    val fullName: String = "",
    val owner: Owner = Owner(),
    val htmlUrl: String = "",
    val description: String = "",
    val language: String? = null,
    val forksCount: Long = -1,
    val stargazersCount: Long = -1,
    val watchersCount: Long = -1,
    val topics: List<String> = emptyList()
)

data class Owner(
    val id: Long = -1,
    val name: String = "",
    val avatarUrl: String = "",
    val type: String = ""
)