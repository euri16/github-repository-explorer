package dev.eury.core.data.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.eury.core.data.api.GithubRepositoriesAPI
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class ApiModule {

    @Provides
    @Reusable
    fun githubReposApi(retrofit: Retrofit): GithubRepositoriesAPI =
        retrofit.create(GithubRepositoriesAPI::class.java)
}
