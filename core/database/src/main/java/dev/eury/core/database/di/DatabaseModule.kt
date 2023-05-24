package dev.eury.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.eury.core.database.GithubRepoDatabase
import dev.eury.core.database.dao.GithubRepoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesGithubRepoDatabase(
        @ApplicationContext context: Context,
    ): GithubRepoDatabase = Room.databaseBuilder(
        context,
        GithubRepoDatabase::class.java,
        "github-repo-db",
    ).build()

    @Provides
    fun providesGithubRepoDao(
        database: GithubRepoDatabase,
    ): GithubRepoDao = database.githubRepoDao()
}