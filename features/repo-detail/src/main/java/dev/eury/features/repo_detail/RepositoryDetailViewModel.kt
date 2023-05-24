package dev.eury.features.repo_detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eury.core.data.repositories.GithubReposRepository
import dev.eury.core.data.utils.ResultOperation
import dev.eury.core.ui_common.base.BaseViewModel
import dev.eury.features.repo_detail.RepositoryDetailUiContract.LoadingState
import dev.eury.features.repo_detail.RepositoryDetailUiContract.UiEffect
import dev.eury.features.repo_detail.RepositoryDetailUiContract.UiEvent
import dev.eury.features.repo_detail.RepositoryDetailUiContract.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor(
    private val githubRepository: GithubReposRepository
) : BaseViewModel<UiEvent, UiState, UiEffect>() {

    private fun loadRepoById(repoId: Long) {
        setState { copy(loadingState = LoadingState.LOADING) }

        viewModelScope.launch {
            when(val operation = githubRepository.getRepoById(repoId)) {
                is ResultOperation.Success -> setState {
                    copy(repository = operation.value, loadingState = LoadingState.NOT_LOADING)
                }
                is ResultOperation.Error -> {
                    setState { copy(loadingState = LoadingState.NOT_LOADING) }
                    setEffect(UiEffect.ShowLoadError)
                }
            }
        }
    }

    override fun processEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OpenGithubPage -> setEffect(UiEffect.OpenGithubPage(event.url))
            is UiEvent.LoadRepoDetails -> loadRepoById(event.repoId)
            UiEvent.MarkEffectAsConsumed -> markEffectAsConsumed()
        }
    }

    override fun getInitialState() = UiState()
}