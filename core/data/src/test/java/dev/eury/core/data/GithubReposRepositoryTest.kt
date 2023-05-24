package dev.eury.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.eury.core.data.api.GithubRepositoriesAPI
import dev.eury.core.data.dto.GithubRepoDtoResponse
import dev.eury.core.data.repositories.GithubReposRepository
import dev.eury.core.data.utils.ResultOperation
import dev.eury.core.database.GithubRepoDatabase
import dev.eury.core.network.calladapter.NetworkResponse
import dev.eury.core.testing.rules.MainCoroutineRule
import dev.eury.core.testing.test_values.GithubRepoTestValues
import dev.eury.core.testing.test_values.GithubRepoTestValues.buildGithubRepo
import dev.eury.core.testing.test_values.GithubRepoTestValues.buildGithubRepoDTOList
import dev.eury.core.testing.test_values.GithubRepoTestValues.buildGithubRepoEntity
import dev.eury.core.testing.test_values.NetworkTestValues
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class GithubReposRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val api: GithubRepositoriesAPI = mockk()
    private val database: GithubRepoDatabase = mockk()

    private val reposRepository = GithubReposRepository(api, database)

    @Test
    fun `test getRepoById returns a non nullable and ResultOperation`() = runTest {
        val id = 10L
        coEvery { database.githubRepoDao().getRepoById(id) } returns
                buildGithubRepoEntity(id, owner = GithubRepoTestValues.buildOwnerDto(id))

        val response = reposRepository.getRepoById(id)

        val expected = ResultOperation.Success(buildGithubRepo(id))

        assertEquals(expected, response)
    }

    @Test
    fun `test getRepoById returns null`() = runTest {
        val id = 10L
        coEvery { database.githubRepoDao().getRepoById(id) } returns null

        val response = reposRepository.getRepoById(id)

        val errorMsg = "value is null when wrapping"
        val expected = NetworkTestValues.mockResultOperationError(
            errorBody = null,
            throwable = IllegalStateException(errorMsg),
            code = null,
        )

        assertTrue((response as ResultOperation.Error).throwable is IllegalStateException)
        assertEquals(response.throwable?.localizedMessage, errorMsg)
        assertEquals(expected.body?.string(), response.body?.string())
        assertEquals(expected.code, response.code)
        assertEquals(expected.isNetworkError, response.isNetworkError)
    }

    @Test
    fun `test searchRepos returns a list of GithubRepo`() = runTest {
        coEvery { api.searchRepos(any(), any(), any()) } returns
                NetworkResponse.Success(
                    GithubRepoDtoResponse(items = buildGithubRepoDTOList(10))
                )

        val response = reposRepository.searchRepos("Android", 10, 1)

        val expected = ResultOperation.Success(GithubRepoTestValues.buildGithubRepoList(10))

        assertEquals(expected, response)
    }

    @Test
    fun `test api searchRepos returns an API Error`() = runTest {
        coEvery { api.searchRepos(any(), any(), any()) } returns
                NetworkTestValues.mockApiError("error body")

        val response = reposRepository.searchRepos("Android", 10, 1)

        val expected = NetworkTestValues.mockResultOperationError(
            errorBody = NetworkTestValues.mockResponseBody("error body"),

        )

        assertNull((response as ResultOperation.Error).throwable)
        assertEquals(expected.body?.string(), response.body?.string())
        assertEquals(expected.code, response.code)
        assertEquals(expected.isNetworkError, response.isNetworkError)
    }

    @Test
    fun `test api searchRepos returns a network Error`() = runTest {
        val errorMsg = "Error"
        coEvery { api.searchRepos(any(), any(), any()) } returns
                NetworkTestValues.mockNetworkError(UnknownHostException(errorMsg))

        val response = reposRepository.searchRepos("Android", 10, 1)

        assertTrue((response as ResultOperation.Error).throwable is IOException)
        assertTrue((response).throwable is UnknownHostException)
        assertEquals(response.throwable?.localizedMessage, errorMsg)
    }
}