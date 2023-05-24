package dev.eury.features.github_repos_list

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import dev.eury.core.models.repositories.GithubRepo
import dev.eury.core.ui_common.base.ViewEffect
import dev.eury.core.ui_common.base.ViewEvent
import dev.eury.core.ui_common.base.ViewState

object GithubRepoListContract {
    data class UiState(
        val repoPagingData: PagingData<GithubRepo> = PagingData.empty(),
        val loadingState: LoadingState = LoadingState.LOADING,
        val isListEmpty: Boolean = false,
        val isInitialLoadDone: Boolean = false
    ) : ViewState

    enum class LoadingState {
        LOADING, LOADED, ERROR
    }

    sealed class UiEffect : ViewEffect {
        data class NavigateToDetail(val repoId: Long) : UiEffect()
        object ShowLoadError : UiEffect()
    }

    sealed class UiEvent : ViewEvent {
        data class NavigateToDetail(val repoId: Long) : UiEvent()
        data class OnLoadStateChanged(
            val loadState: CombinedLoadStates,
            val adapterItemCount: Int
        ) : UiEvent()
        object OnInitialLoadDone : UiEvent()

        object MarkEffectAsConsumed : UiEvent()
    }
}