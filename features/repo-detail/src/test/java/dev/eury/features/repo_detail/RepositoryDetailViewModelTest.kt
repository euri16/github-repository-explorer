package dev.eury.features.repo_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import dev.eury.core.data.repositories.GithubReposRepository
import dev.eury.core.data.utils.ResultOperation.Companion.wrapSuccess
import dev.eury.core.testing.rules.MainCoroutineRule
import dev.eury.core.testing.test_values.GithubRepoTestValues
import dev.eury.core.testing.test_values.NetworkTestValues
import dev.eury.features.repo_detail.RepositoryDetailUiContract.UiEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val repository: GithubReposRepository = mockk()

    private val viewModel = RepositoryDetailViewModel(repository)

    @Test
    fun `test load repo succeed`() = runTest {
        val repoId = 10L

        coEvery { repository.getRepoById(repoId) } returns
                GithubRepoTestValues.buildGithubRepo(repoId).wrapSuccess()

        viewModel.processEvent(UiEvent.LoadRepoDetails(repoId))

        viewModel.viewState.test {
            val newItem = awaitItem()
            assertEquals(GithubRepoTestValues.buildGithubRepo(repoId), newItem.repository)
        }
    }

    @Test
    fun `test load repo api error`() = runTest {
        coEvery { repository.getRepoById(any()) } returns
                NetworkTestValues.mockResultOperationError()

        viewModel.processEvent(UiEvent.LoadRepoDetails(10))

        viewModel.effect.test {
            assertEquals(RepositoryDetailUiContract.UiEffect.ShowLoadError, awaitItem())
        }
    }
}