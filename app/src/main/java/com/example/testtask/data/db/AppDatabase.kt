package com.example.testtask.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testtask.data.db.dao.GifDao
import com.example.testtask.data.db.dao.GifRemoteKeysDao
import com.example.testtask.data.db.entity.GifEntity
import com.example.testtask.data.db.entity.GifRemoteKeysEntity

@Database(
    entities = [GifEntity::class, GifRemoteKeysEntity::class],
    version = DatabaseInfo.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DatabaseInfo.DATABASE_NAME
            ).build()
        }
    }

    abstract fun gifDao(): GifDao
    abstract fun remoteKeysDao(): GifRemoteKeysDao
}
