package dev.eury.features.repo_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.eury.core.ui_common.R.*
import dev.eury.core.ui_common.base.BaseBindingFragment
import dev.eury.core.ui_common.components.TopicsAdapter
import dev.eury.core.ui_common.utils.getDrawableCompat
import dev.eury.core.ui_common.utils.load
import dev.eury.core.utils.openUrlInBrowser
import dev.eury.features.repo_detail.RepositoryDetailUiContract.LoadingState
import dev.eury.features.repo_detail.RepositoryDetailUiContract.UiEffect
import dev.eury.features.repo_detail.RepositoryDetailUiContract.UiEvent
import dev.eury.features.repo_detail.RepositoryDetailUiContract.UiState
import dev.eury.features.repo_detail.databinding.FragmentRepoDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryDetailFragment : BaseBindingFragment<FragmentRepoDetailBinding>() {

    private val viewModel: RepositoryDetailViewModel by viewModels()

    private val repoId: Long by lazy {
        arguments?.getLong(KEY_REPO_ID)
            ?: throw IllegalStateException("A Repo Id need to be provided")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.processEvent(UiEvent.LoadRepoDetails(repoId))

        observeFlows()
    }

    private fun observeFlows() {
        lifecycleScope.launch {
            viewModel.viewState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { state -> bindData(state) }
        }

        lifecycleScope.launch {
            viewModel.effect.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .filterNotNull()
                .collect { effect ->
                    when (effect) {
                        is UiEffect.OpenGithubPage -> {
                            runCatching { openUrlInBrowser(effect.url) }
                                .onFailure {
                                    Snackbar.make(
                                        requireView(),
                                        R.string.error_opening_url,
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                        }

                        UiEffect.ShowLoadError -> {
                            Snackbar.make(
                                requireView(),
                                string.error_loading_data,
                                Snackbar.LENGTH_LONG
                            ).setAction(string.retry) {
                                viewModel.processEvent(UiEvent.LoadRepoDetails(repoId))
                            }.show()
                        }
                    }

                    viewModel.processEvent(UiEvent.MarkEffectAsConsumed)
                }
        }
    }

    private fun bindData(state: UiState) {
        with(binding) {
            progress.isVisible = state.loadingState == LoadingState.LOADING
            groupContent.isVisible = state.loadingState == LoadingState.NOT_LOADING

            if (state.loadingState == LoadingState.LOADING) {
                return
            }

            val repository = state.repository
            val openUrlClickListener = OnClickListener {
                viewModel.processEvent(UiEvent.OpenGithubPage(repository.htmlUrl))
            }

            with(tvName) {
                text = repository.name
                setOnClickListener(openUrlClickListener)
            }
            ivOpen.setOnClickListener(openUrlClickListener)
            tvDescription.text = repository.description
            tvStarsCount.text = repository.stargazersCount.toString()
            tvForks.text = repository.forksCount.toString()
            tvLanguage.text = repository.language
            tvOwnerName.text = repository.owner.name
            tvOwnerType.text = repository.owner.type
            ivUserAvatar.load(
                repository.owner.avatarUrl,
                cornerRadius = AVATAR_CORNER_RADIUS,
                drawablePlaceHolder = getDrawableCompat(R.drawable.img_placeholder)
            )

            rvTopics.adapter = TopicsAdapter().also {
                it.submitList(repository.topics.take(MAX_TOPICS))
            }
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRepoDetailBinding.inflate(inflater, container, false)

    companion object {
        const val KEY_REPO_ID = "repo_id"
        const val AVATAR_CORNER_RADIUS = 45
        const val MAX_TOPICS = 12
    }
}