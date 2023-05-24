package dev.eury.core.data.repositories

import androidx.paging.PagingSource
import androidx.room.withTransaction
import dev.eury.core.data.api.GithubRepositoriesAPI
import dev.eury.core.data.mappers.toEntity
import dev.eury.core.data.mappers.toModel
import dev.eury.core.data.utils.ResultOperation
import dev.eury.core.data.utils.ResultOperation.Companion.wrapNullable
import dev.eury.core.data.utils.asOperation
import dev.eury.core.database.GithubRepoDatabase
import dev.eury.core.database.entity.GithubRepoEntity
import dev.eury.core.database.entity.RemoteKeysEntity
import dev.eury.core.database.entity.toModel
import dev.eury.core.models.repositories.GithubRepo
import javax.inject.Inject

class GithubReposRepository @Inject constructor(
    private val api: GithubRepositoriesAPI,
    private val database: GithubRepoDatabase
) {

    suspend fun getRepoById(repoId: Long): ResultOperation<GithubRepo> {
        return database.githubRepoDao().getRepoById(repoId)
            .wrapNullable()
            .map { it.toModel() }
    }

    fun getReposByName(query: String): PagingSource<Int, GithubRepoEntity> {
        return database.githubRepoDao().reposByName(query)
    }

    suspend fun searchRepos(
        query: String,
        limit: Int,
        page: Int
    ): ResultOperation<List<GithubRepo>> {
        return api.searchRepos(query, limit, page)
            .map { list -> list.items.map { it.toModel() } }
            .asOperation()
    }

    internal suspend fun clearDatabase() {
        database.withTransaction {
            database.remoteKeysDao().clearRemoteKeys()
            database.githubRepoDao().clearRepos()
        }
    }

    internal suspend fun insertRemoteKeys(keys: List<RemoteKeysEntity>) {
        database.withTransaction { database.remoteKeysDao().insertAll(keys) }
    }

    internal suspend fun insertRepos(repos: List<GithubRepo>) {
        database.withTransaction {
            database.githubRepoDao().insertOrUpdateRepos(repos.map { it.toEntity() })
        }
    }

    internal suspend fun getRemoteKeysByRepoId(repoId: Long): RemoteKeysEntity? {
        return database.remoteKeysDao().getRemoteKeysByRepoId(repoId)
    }
}
