package dev.eury.core.testing.test_values

import dev.eury.core.data.dto.GithubRepoDTO
import dev.eury.core.data.dto.OwnerDTO
import dev.eury.core.database.entity.GithubRepoEntity
import dev.eury.core.models.repositories.GithubRepo
import dev.eury.core.models.repositories.Owner

object GithubRepoTestValues {
    fun buildGithubRepoDTO(
        id: Long = 1L,
        name: String? = null,
        fullName: String? = null,
        ownerDTO: OwnerDTO? = null,
        htmlUrl: String? = null,
        description: String? = null,
        language: String? = null,
        forksCount: Long? = null,
        starsCount: Long? = null,
        watchersCount: Long? = null,
        topics: List<String>? = null
    ) = GithubRepoDTO(
        id = id,
        name = name ?: "Repo Name #$id",
        fullName = fullName ?: "Full Name #$id",
        owner = ownerDTO ?: buildOwnerDto(id = id),
        htmlUrl = htmlUrl ?: "https://github.com/repourl",
        description = description ?: "Repo description $id",
        language = language ?: "Kotlin",
        forksCount = forksCount ?: -1,
        stargazersCount = starsCount ?: -1,
        watchersCount = watchersCount ?: -1,
        topics = topics ?: listOf("Android", "Kotlin", "Coroutines")
    )

    fun buildOwnerDto(
        id: Long = 1
    ) = OwnerDTO(
        id = id,
        name = "owner name #$id",
        avatarUrl = "http://dummy.com",
        type = "user"
    )

    fun buildGithubRepoEntity(
        id: Long,
        name: String? = null,
        fullName: String? = null,
        owner: OwnerDTO? = null,
        htmlUrl: String? = null,
        description: String? = null,
        language: String? = null,
        forksCount: Long? = null,
        starsCount: Long? = null,
        watchersCount: Long? = null,
        topics: List<String>? = null
    ) = GithubRepoEntity(
        id = id,
        name = name ?: "Repo Name #$id",
        fullName = fullName ?: "Full Name #$id",
        ownerId = owner?.id ?: id,
        ownerName = owner?.name ?: buildOwner(id).name,
        ownerAvatarUrl = owner?.avatarUrl ?: buildOwner(id).avatarUrl,
        ownerType = owner?.type ?: buildOwner(id).type,
        htmlUrl = htmlUrl ?: "https://github.com/repourl",
        description = description ?: "Repo description $id",
        language = language ?: "Kotlin",
        forksCount = forksCount ?: -1,
        stargazersCount = starsCount ?: -1,
        watchersCount = watchersCount ?: -1,
        topics = topics ?: listOf("Android", "Kotlin", "Coroutines")
    )

    fun buildGithubRepo(
        id: Long = 1L,
        name: String? = null,
        fullName: String? = null,
        owner: Owner? = null,
        htmlUrl: String? = null,
        description: String? = null,
        language: String? = null,
        forksCount: Long? = null,
        starsCount: Long? = null,
        watchersCount: Long? = null,
        topics: List<String>? = null
    ) = GithubRepo(
        id = id,
        name = name ?: "Repo Name #$id",
        fullName = fullName ?: "Full Name #$id",
        owner = owner ?: buildOwner(id = id),
        htmlUrl = htmlUrl ?: "https://github.com/repourl",
        description = description ?: "Repo description $id",
        language = language ?: "Kotlin",
        forksCount = forksCount ?: -1,
        stargazersCount = starsCount ?: -1,
        watchersCount = watchersCount ?: -1,
        topics = topics ?: listOf("Android", "Kotlin", "Coroutines")
    )

    private fun buildOwner(
        id: Long = 1
    ) = Owner(
        id = id,
        name = "owner name #$id",
        avatarUrl = "http://dummy.com",
        type = "user"
    )

    fun buildGithubRepoDTOList(amountOfItems: Int) = (1L..amountOfItems).map {
        buildGithubRepoDTO(it)
    }

    fun buildGithubRepoList(amountOfItems: Int) = (1L..amountOfItems).map {
        buildGithubRepo(it)
    }
}