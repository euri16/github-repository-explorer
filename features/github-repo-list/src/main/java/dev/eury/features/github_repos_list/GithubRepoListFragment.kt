package dev.eury.features.github_repos_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.eury.core.ui_common.R.drawable
import dev.eury.core.ui_common.R.string
import dev.eury.core.ui_common.base.BaseBindingFragment
import dev.eury.core.ui_common.navigation.NavigationDirections
import dev.eury.core.ui_common.utils.addVerticalItemDecoration
import dev.eury.features.githubReposList.databinding.FragmentGithubReposListBinding
import dev.eury.features.github_repos_list.GithubRepoListContract.LoadingState
import dev.eury.features.github_repos_list.GithubRepoListContract.UiEvent
import dev.eury.features.github_repos_list.adapter.GithubReposLoadStateAdapter
import dev.eury.features.github_repos_list.adapter.GithubRepositoryAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GithubRepoListFragment : BaseBindingFragment<FragmentGithubReposListBinding>() {

    private val viewModel: GithubRepoListViewModel by viewModels()

    private val adapter by lazy { GithubRepositoryAdapter(::onRepoSelected) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter(savedInstanceState)
        observeFlows()
    }

    private fun setupAdapter(savedInstanceState: Bundle?) {
        with(binding.rvList) {
            setHasFixedSize(true)

            val linearLayoutManager = LinearLayoutManager(context)
                .also { layoutManager = it }

            addVerticalItemDecoration(drawable.recycler_divider_line)

            val pagingDataAdapter = this@GithubRepoListFragment.adapter

            adapter = pagingDataAdapter.withLoadStateFooter(
                footer = GithubReposLoadStateAdapter()
            )

            if (savedInstanceState == null) {
                pagingDataAdapter.addOnPagesUpdatedListener {
                    val state = viewModel.viewState.value
                    Log.d(
                        "PagesUpdatedListener",
                        "${pagingDataAdapter.itemCount} | initiaLoad = ${state.isInitialLoadDone}"
                    )
                    if (pagingDataAdapter.itemCount > MIN_SCROLL && !state.isInitialLoadDone) {
                        binding.rvList.post {
                            linearLayoutManager.scrollToPosition(0)
                        }
                        viewModel.processEvent(UiEvent.OnInitialLoadDone)
                    }
                }
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { loadState ->
                    viewModel.processEvent(UiEvent.OnLoadStateChanged(loadState, adapter.itemCount))
                }
        }
    }

    private fun observeFlows() {
        lifecycleScope.launch {
            viewModel.viewState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { uiState ->
                    binding.rvList.isVisible = uiState.loadingState == LoadingState.LOADED
                    binding.progress.isVisible = uiState.loadingState == LoadingState.LOADING

                    adapter.submitData(lifecycle, uiState.repoPagingData)
                }
        }

        lifecycleScope.launch {
            viewModel.effect.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .filterNotNull()
                .collect { effect ->
                    when (effect) {
                        is GithubRepoListContract.UiEffect.NavigateToDetail -> {
                            navigate(NavigationDirections.RepoListToDetail(effect.repoId))
                        }

                        GithubRepoListContract.UiEffect.ShowLoadError -> {
                            Snackbar.make(
                                requireView(),
                                string.error_loading_data,
                                Snackbar.LENGTH_INDEFINITE
                            ).apply {
                                setAction(string.retry) {
                                    adapter.retry()
                                    dismiss()
                                }
                            }.also { it.show() }
                        }
                    }
                    viewModel.processEvent(UiEvent.MarkEffectAsConsumed)
                }
        }
    }

    private fun onRepoSelected(repoId: Long) {
        viewModel.processEvent(UiEvent.NavigateToDetail(repoId))
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentGithubReposListBinding.inflate(inflater, container, false)

    companion object {
        const val MIN_SCROLL = 1
    }
}