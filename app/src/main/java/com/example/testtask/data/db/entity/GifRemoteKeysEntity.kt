package com.example.testtask.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testtask.data.db.DatabaseInfo

@Entity(tableName = DatabaseInfo.TABLE_GIF_REMOTE_KEYS)
data class GifRemoteKeysEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = DatabaseInfo.PREVIOUS_PAGE) val prevPage: Int?,
    @ColumnInfo(name = DatabaseInfo.NEXT_PAGE) val nextPage: Int?,
    @ColumnInfo(name = DatabaseInfo.LAST_UPDATED) val lastUpdated: Long
)
