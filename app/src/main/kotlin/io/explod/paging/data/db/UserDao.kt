package io.explod.paging.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.explod.paging.data.model.User

@Dao
abstract class UserDao {
    @Query("SELECT * FROM users ORDER BY id ASC")
    abstract fun pagedUsers(): DataSource.Factory<Int, User>

    @Insert(onConflict = REPLACE)
    abstract fun insertOrReplace(users: Collection<User>)
}