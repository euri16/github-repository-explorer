package dev.eury.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.eury.core.models.repositories.GithubRepo
import dev.eury.core.models.repositories.Owner

@Entity(tableName = "repositories")
data class GithubRepoEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val fullName: String,
    val htmlUrl: String,
    val description: String,
    val language: String?,
    val forksCount: Long,
    val stargazersCount: Long,
    val watchersCount: Long,
    val ownerId: Long,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val ownerType: String,
    val topics: List<String>
)

fun GithubRepoEntity.toModel() = GithubRepo(
    id = id,
    name = name,
    fullName = fullName,
    htmlUrl = htmlUrl,
    description = description,
    language = language,
    forksCount = forksCount,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    owner = Owner(ownerId, ownerName, ownerAvatarUrl, ownerType),
    topics = topics
)
