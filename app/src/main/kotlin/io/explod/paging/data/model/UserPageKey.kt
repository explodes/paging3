package io.explod.paging.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_page_key")
data class UserPageKey(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 1L, // singleton, should only have one row, with an id of 1.

    @ColumnInfo(name = "key")
    val key: String?,
)
