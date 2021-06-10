package io.explod.paging.inject

import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.explod.paging.app.App
import io.explod.paging.data.db.AppDatabase

@Module
class DatabaseModule {
    @Provides
    fun appDatabase(app: App): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, databaseName)
            .addMigrations(*AppDatabase.migrations().toTypedArray())
            .build()
    }

    companion object {
        private const val databaseName = "paging.db"
    }
}