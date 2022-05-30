package com.example.testtask.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.data.db.entity.GifEntity

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGifs(gifs: List<GifEntity>)

    @Query("SELECT * FROM tableGifs ORDER BY pK ASC")
    fun getAllGifs(): PagingSource<Int, GifEntity>

    @Query("SELECT * FROM tableGifs WHERE pK >= (SELECT pK FROM tableGifs WHERE gifId = :fromId) ORDER BY pK")
    fun getAllGifsFrom(fromId: String): PagingSource<Int, GifEntity>

    @Query("SELECT * FROM tableGifs WHERE gifId = :id")
    suspend fun getGif(id: String): GifEntity

    @Query("DELETE FROM tableGifs WHERE gifId = :id")
    suspend fun deleteGif(id: String)

    @Query("DELETE FROM tableGifs")
    suspend fun deleteAllGifs()
}
