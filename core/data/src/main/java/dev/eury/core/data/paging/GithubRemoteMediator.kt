package dev.eury.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.eury.core.data.repositories.GithubReposRepository
import dev.eury.core.data.utils.ResultOperation
import dev.eury.core.database.GithubRepoDatabase
import dev.eury.core.database.entity.GithubRepoEntity
import dev.eury.core.database.entity.RemoteKeysEntity
import kotlinx.coroutines.delay
import timber.log.Timber

private const val GITHUB_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator(
    private val query: String,
    private val repository: GithubReposRepository
) : RemoteMediator<Int, GithubRepoEntity>() {

    // TODO: Uncomment to test error
    //var isFirstLoad = true

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GithubRepoEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        // TODO: Uncomment to test error
        /*if(isFirstLoad) {
            isFirstLoad = false
            return MediatorResult.Error(IllegalStateException(""))
        }*/

        return when (val response = repository.searchRepos(query, state.config.pageSize, page)) {
            is ResultOperation.Success -> {

                Timber.d("Pagination: API called for page=$page")

                val repos = response.value
                val endOfPaginationReached = repos.isEmpty()

                if (loadType == LoadType.REFRESH) {
                    repository.clearDatabase()
                }

                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKeysEntity(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                with(repository) {
                    insertRemoteKeys(keys)
                    insertRepos(repos)
                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }

            is ResultOperation.Error -> {
                val throwable =
                    response.throwable ?: IllegalStateException("An unexpected API error ocurred")
                MediatorResult.Error(throwable)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, GithubRepoEntity>
    ): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo -> repository.getRemoteKeysByRepoId(repo.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, GithubRepoEntity>
    ): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo -> repository.getRemoteKeysByRepoId(repo.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GithubRepoEntity>
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                repository.getRemoteKeysByRepoId(repoId)
            }
        }
    }

}