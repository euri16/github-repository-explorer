package dev.eury.features.repo_detail

import dev.eury.core.models.repositories.GithubRepo
import dev.eury.core.ui_common.base.ViewEffect
import dev.eury.core.ui_common.base.ViewEvent
import dev.eury.core.ui_common.base.ViewState

object RepositoryDetailUiContract {
    data class UiState(
        val repository: GithubRepo = GithubRepo(),
        val loadingState: LoadingState = LoadingState.LOADING
    ) : ViewState

    enum class LoadingState {
        LOADING, NOT_LOADING
    }

    sealed class UiEffect : ViewEffect {
        data class OpenGithubPage(val url: String) : UiEffect()
        object ShowLoadError : UiEffect()
    }

    sealed class UiEvent : ViewEvent {
        data class OpenGithubPage(val url: String) : UiEvent()
        data class LoadRepoDetails(val repoId: Long) : UiEvent()
        object MarkEffectAsConsumed : UiEvent()
    }
}