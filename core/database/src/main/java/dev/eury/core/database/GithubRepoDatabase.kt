package dev.eury.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.eury.core.database.converters.StringListConverter
import dev.eury.core.database.dao.GithubRepoDao
import dev.eury.core.database.dao.RemoteKeysDao
import dev.eury.core.database.entity.GithubRepoEntity
import dev.eury.core.database.entity.RemoteKeysEntity

@Database(
    entities = [GithubRepoEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(StringListConverter::class)
abstract class GithubRepoDatabase : RoomDatabase() {
    abstract fun githubRepoDao(): GithubRepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}