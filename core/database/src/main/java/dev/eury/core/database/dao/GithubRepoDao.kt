package dev.eury.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.eury.core.database.entity.GithubRepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubRepoDao {

    @Query(value = "SELECT * FROM repositories")
    fun getGithubRepos(): Flow<List<GithubRepoEntity>>

    @Query(value = "SELECT * FROM repositories where id = :id")
    suspend fun getRepoById(id: Long): GithubRepoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateRepos(repoEntities: List<GithubRepoEntity>): List<Long>

    @Query(
        "SELECT * FROM repositories WHERE " +
                "name LIKE :queryString OR description LIKE :queryString " +
                "ORDER BY stargazersCount DESC, name ASC"
    )
    fun reposByName(queryString: String): PagingSource<Int, GithubRepoEntity>

    @Query("DELETE FROM repositories")
    suspend fun clearRepos()
}