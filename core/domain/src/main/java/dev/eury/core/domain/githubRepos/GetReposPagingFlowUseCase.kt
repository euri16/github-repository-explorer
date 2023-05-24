package dev.eury.core.domain.githubRepos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.eury.core.data.paging.GithubRemoteMediator
import dev.eury.core.data.repositories.GithubReposRepository
import dev.eury.core.database.entity.toModel
import dev.eury.core.models.repositories.GithubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val NETWORK_PAGE_SIZE = 30
private const val IN_QUALIFIER = "in:name,description"

@OptIn(ExperimentalPagingApi::class)
class GetReposPagingFlowUseCase @Inject constructor(
    private val repository: GithubReposRepository
) {

    operator fun invoke(
        query: String
    ): Flow<PagingData<GithubRepo>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { repository.getReposByName(dbQuery) }

        val apiQuery = query + IN_QUALIFIER

        // TODO: Test without the prefetch distance and max size
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 5,
                maxSize = NETWORK_PAGE_SIZE + (2 * 15),
                enablePlaceholders = true
            ),
            remoteMediator = GithubRemoteMediator(
                apiQuery,
                repository
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData -> pagingData.map { entity -> entity.toModel() } }
    }
}