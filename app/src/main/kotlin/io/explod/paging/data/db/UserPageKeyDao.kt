package io.explod.paging.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.explod.paging.data.model.UserPageKey

@Dao
interface UserPageKeyDao {
    @Query("SELECT * FROM user_page_key WHERE id = 1")
    fun maybeGetKey(): UserPageKey?

    @Insert(onConflict = REPLACE) // insert only one userPageKey
    fun insertOrReplace(key: UserPageKey)
}