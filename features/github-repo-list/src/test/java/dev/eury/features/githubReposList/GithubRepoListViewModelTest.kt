package dev.eury.features.githubReposList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.LoadState
import app.cash.turbine.test
import dev.eury.core.data.api.GithubRepositoriesAPI
import dev.eury.core.data.dto.GithubRepoDtoResponse
import dev.eury.core.data.repositories.GithubReposRepository
import dev.eury.core.domain.githubRepos.GetReposPagingFlowUseCase
import dev.eury.core.network.calladapter.NetworkResponse
import dev.eury.core.testing.rules.MainCoroutineRule
import dev.eury.core.testing.test_values.GithubRepoTestValues
import dev.eury.core.testing.test_values.LoadStatesTestValues
import dev.eury.features.github_repos_list.GithubRepoListContract.LoadingState
import dev.eury.features.github_repos_list.GithubRepoListContract.UiEffect
import dev.eury.features.github_repos_list.GithubRepoListContract.UiEvent
import dev.eury.features.github_repos_list.GithubRepoListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GithubRepoListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: GithubRepoListViewModel

    @Before
    fun before() {
        viewModel = GithubRepoListViewModel(mockk(relaxed = true))
    }

    @Test
    fun `when sending navigate event, effect should be updated`() = runTest {
        val repoId = 10L
        viewModel.processEvent(UiEvent.NavigateToDetail(repoId))

        viewModel.effect.test {
            assertEquals(UiEffect.NavigateToDetail(repoId), awaitItem())
        }
    }

    @Test
    fun `if initial load is NotLoading and adapter is empty should notify empty list`() = runTest {
        viewModel.processEvent(
            UiEvent.OnLoadStateChanged(
                LoadStatesTestValues.getCombinedLoadState(), 0
            )
        )

        viewModel.viewState.test {
            assertTrue(awaitItem().isListEmpty)
        }
    }

    @Test
    fun `if initial load is Loading should set state is loading`() = runTest {
        val mediator = LoadStatesTestValues.getLoadStates(refresh = LoadState.Loading)
        viewModel.processEvent(
            UiEvent.OnLoadStateChanged(
                LoadStatesTestValues.getCombinedLoadState(mediator = mediator), 0
            )
        )

        viewModel.viewState.test {
            assertTrue(awaitItem().isListEmpty)
        }
    }

    @Test
    fun `if initial api loaded and db is loaded should set state is loaded`() = runTest {
        val source = LoadStatesTestValues.getLoadStates(refresh = LoadState.NotLoading(false))
        val mediator = LoadStatesTestValues.getLoadStates(refresh = LoadState.NotLoading(false))
        viewModel.processEvent(
            UiEvent.OnLoadStateChanged(
                LoadStatesTestValues.getCombinedLoadState(
                    mediator = mediator,
                    source = source
                ),
                adapterItemCount = 0
            )
        )

        viewModel.viewState.test {
            assertEquals(LoadingState.LOADED, awaitItem().loadingState)
        }
    }

    @Test
    fun `if is error refreshing and items greater than 0 should set effect is load error`() =
        runTest {
            val mediator =
                LoadStatesTestValues.getLoadStates(refresh = LoadState.Error(IllegalStateException("")))
            viewModel.processEvent(
                UiEvent.OnLoadStateChanged(
                    LoadStatesTestValues.getCombinedLoadState(
                        mediator = mediator,
                    ),
                    adapterItemCount = 10
                )
            )

            viewModel.effect.test {
                assertEquals(UiEffect.ShowLoadError, awaitItem())
            }
        }

    @Test
    fun `if is error refreshing and items empty should set effect is load error`() = runTest {
        val mediator =
            LoadStatesTestValues.getLoadStates(refresh = LoadState.Error(IllegalStateException("")))
        viewModel.processEvent(
            UiEvent.OnLoadStateChanged(
                LoadStatesTestValues.getCombinedLoadState(
                    mediator = mediator,
                ),
                adapterItemCount = 0
            )
        )

        viewModel.effect.test {
            assertEquals(UiEffect.ShowLoadError, awaitItem())
        }
    }

    @Test
    fun `if db append error should set effect is load error`() = runTest {
        val source =
            LoadStatesTestValues.getLoadStates(append = LoadState.Error(IllegalStateException("")))
        viewModel.processEvent(
            UiEvent.OnLoadStateChanged(
                LoadStatesTestValues.getCombinedLoadState(
                    source = source,
                ),
                adapterItemCount = 0
            )
        )

        viewModel.effect.test {
            assertEquals(UiEffect.ShowLoadError, awaitItem())
        }
    }

    @Test
    fun `if db prepend error should set effect is load error`() = runTest {
        val source =
            LoadStatesTestValues.getLoadStates(prepend = LoadState.Error(IllegalStateException("")))
        viewModel.processEvent(
            UiEvent.OnLoadStateChanged(
                LoadStatesTestValues.getCombinedLoadState(
                    source = source,
                ),
                adapterItemCount = 0
            )
        )

        viewModel.effect.test {
            assertEquals(UiEffect.ShowLoadError, awaitItem())
        }
    }

    @Test
    fun `if api append error should set effect is load error`() = runTest {
        viewModel.processEvent(
            UiEvent.OnLoadStateChanged(
                LoadStatesTestValues.getCombinedLoadState(
                    append = LoadState.Error(IllegalStateException(""))
                ),
                adapterItemCount = 0
            )
        )

        viewModel.effect.test {
            assertEquals(UiEffect.ShowLoadError, awaitItem())
        }
    }

    @Test
    fun `if api prepend error should set effect is load error`() = runTest {
        viewModel.processEvent(
            UiEvent.OnLoadStateChanged(
                LoadStatesTestValues.getCombinedLoadState(
                    prepend = LoadState.Error(IllegalStateException(""))
                ),
                adapterItemCount = 0
            )
        )

        viewModel.effect.test {
            assertEquals(UiEffect.ShowLoadError, awaitItem())
        }
    }

    @Test
    fun `if initial load set to done should update state`() = runTest {
        viewModel.processEvent(UiEvent.OnInitialLoadDone)

        viewModel.viewState.test {
            assertTrue(awaitItem().isInitialLoadDone)
        }
    }

    @Test
    fun `if effect marked as consumed then null is emitted in the effects flow`() = runTest {
        viewModel.processEvent(UiEvent.OnInitialLoadDone)

        viewModel.viewState.test {
            assertTrue(awaitItem().isInitialLoadDone)
        }

        viewModel.processEvent(UiEvent.MarkEffectAsConsumed)

        viewModel.effect.test {
            assertNull(awaitItem())
        }
    }
}