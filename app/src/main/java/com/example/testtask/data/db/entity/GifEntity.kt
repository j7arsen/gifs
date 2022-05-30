package com.example.testtask.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testtask.data.db.DatabaseInfo

@Entity(tableName = DatabaseInfo.TABLE_GIFS)
data class GifEntity(
    @PrimaryKey(autoGenerate = true) val pK: Long = 0,
    @ColumnInfo(name = DatabaseInfo.GIF_ID) val gifId: String,
    @ColumnInfo(name = DatabaseInfo.GIF_TITLE) val title: String,
    @ColumnInfo(name = DatabaseInfo.GIF_URL) val url: String,
    @ColumnInfo(name = DatabaseInfo.GIF_CREATE_DATE) val createDate: String
)
