package io.explod.paging.data.db

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import io.explod.paging.data.model.User
import io.explod.paging.data.model.UserPageKey

const val DATABASE_VERSION = 1

@androidx.room.Database(
    entities = [
        User::class,
        UserPageKey::class,
    ],
    exportSchema = true,
    version = DATABASE_VERSION,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun users(): UserDao
    abstract fun userPageKeys(): UserPageKeyDao

    companion object {
        fun migrations(): List<Migration> {
            return listOf()
        }
    }
}