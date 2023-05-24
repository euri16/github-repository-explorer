package dev.eury.core.data.api

import dev.eury.core.data.dto.GithubRepoDtoResponse
import dev.eury.core.network.calladapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepositoriesAPI {

    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("per_page") limit: Int,
        @Query("page") page: Int = 1
    ) : NetworkResponse<GithubRepoDtoResponse>
}