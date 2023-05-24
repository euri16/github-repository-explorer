package dev.eury.core.data.mappers

import dev.eury.core.data.dto.GithubRepoDTO
import dev.eury.core.data.dto.OwnerDTO
import dev.eury.core.database.entity.GithubRepoEntity
import dev.eury.core.models.repositories.GithubRepo
import dev.eury.core.models.repositories.Owner

internal fun GithubRepoDTO.toModel() = GithubRepo(
    id = id,
    name = name,
    fullName = fullName,
    owner = owner.toModel(),
    htmlUrl = htmlUrl,
    description = description,
    language = language,
    forksCount = forksCount,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    topics = topics
)

internal fun GithubRepo.toEntity() = GithubRepoEntity(
    id = id,
    name = name,
    fullName = fullName,
    ownerId = owner.id,
    ownerName = owner.name,
    ownerAvatarUrl = owner.avatarUrl,
    ownerType = owner.type,
    htmlUrl = htmlUrl,
    description = description,
    language = language,
    forksCount = forksCount,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    topics = topics
)

private fun OwnerDTO.toModel() = Owner(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    type = type
)