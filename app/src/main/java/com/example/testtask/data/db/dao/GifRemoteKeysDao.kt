package com.example.testtask.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.data.db.entity.GifRemoteKeysEntity

@Dao
interface GifRemoteKeysDao {
    @Query("SELECT * FROM tableGifRemoteKeys WHERE id = :gifId")
    suspend fun getGifRemoteKeys(gifId: String): GifRemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllGifRemoteKeys(gifRemoteKeys: List<GifRemoteKeysEntity>)

    @Query("DELETE FROM tableGifRemoteKeys")
    suspend fun deleteAllGifRemoteKeys()

    @Query("SELECT MAX(lastUpdated) FROM tableGifRemoteKeys")
    suspend fun getLastUpdated(): Long?
}
