package dev.eury.core.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.eury.core.data.repositories.GithubReposRepository
import dev.eury.core.data.utils.ResultOperation.Companion.wrapSuccess
import dev.eury.core.database.GithubRepoDatabase
import dev.eury.core.testing.TestApplication
import dev.eury.core.testing.rules.MainCoroutineRule
import dev.eury.core.testing.test_values.GithubRepoTestValues
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class GithubDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var database: GithubRepoDatabase
    private lateinit var repository: GithubReposRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            GithubRepoDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = GithubReposRepository(mockk(), database)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun `test getRepoById from the database`() = runTest {
        val repoId = 25L

        database.githubRepoDao().insertOrUpdateRepos(
            listOf(GithubRepoTestValues.buildGithubRepoEntity(repoId))
        )

        val response = repository.getRepoById(repoId)
        val expected = GithubRepoTestValues.buildGithubRepo(repoId).wrapSuccess()

        assertEquals(expected, response)
    }
}