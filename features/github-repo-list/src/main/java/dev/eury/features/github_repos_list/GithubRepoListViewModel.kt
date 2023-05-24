package dev.eury.features.github_repos_list

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eury.core.domain.githubRepos.GetReposPagingFlowUseCase
import dev.eury.core.ui_common.base.BaseViewModel
import dev.eury.features.github_repos_list.GithubRepoListContract.LoadingState
import dev.eury.features.github_repos_list.GithubRepoListContract.UiEffect
import dev.eury.features.github_repos_list.GithubRepoListContract.UiEvent
import dev.eury.features.github_repos_list.GithubRepoListContract.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubRepoListViewModel @Inject constructor(
    getReposPaginatedFlowUseCase: GetReposPagingFlowUseCase
) : BaseViewModel<UiEvent, UiState, UiEffect>() {

    init {
        viewModelScope.launch {
            getReposPaginatedFlowUseCase(SEARCH_QUERY)
                .cachedIn(viewModelScope)
                .collectLatest {
                    setState { copy(repoPagingData = it) }
                }
        }
    }

    override fun processEvent(event: UiEvent) {
        when (event) {
            is UiEvent.NavigateToDetail -> setEffect(UiEffect.NavigateToDetail(event.repoId))

            is UiEvent.OnLoadStateChanged ->
                handleLoadStateChanged(event.loadState, event.adapterItemCount)

            UiEvent.OnInitialLoadDone -> setState { copy(isInitialLoadDone = true) }
            UiEvent.MarkEffectAsConsumed -> markEffectAsConsumed()
        }
    }

    private fun handleLoadStateChanged(loadState: CombinedLoadStates, adapterItemCount: Int) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && adapterItemCount == 0

        val isLoaded = loadState.source.refresh is LoadState.NotLoading ||
                loadState.mediator?.refresh is LoadState.NotLoading

        val isLoadingInitialRequest = loadState.mediator?.refresh is LoadState.Loading

        val isErrorRefreshing = loadState.mediator
            ?.refresh
            ?.let { it is LoadState.Error && adapterItemCount > 0 }
            ?: false

        val isErrorInitialRequest = loadState.mediator?.refresh is LoadState.Error
                && adapterItemCount == 0

        val isErrorLocalOrRemote = (loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error).let { it != null }

        if (isErrorRefreshing || isErrorInitialRequest || isErrorLocalOrRemote) {
            setEffect(UiEffect.ShowLoadError)
        }

        // TODO: Test with order inverted
        val loadingState = when {
            isLoadingInitialRequest -> LoadingState.LOADING
            isLoaded -> LoadingState.LOADED
            else -> LoadingState.ERROR
        }

        setState {
            copy(isListEmpty = isListEmpty, loadingState = loadingState)
        }

    }

    override fun getInitialState() = UiState()

    companion object {
        private const val SEARCH_QUERY = "Android"
    }

}