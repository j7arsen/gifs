package com.example.testtask.domain.datasource

import com.example.testtask.data.db.entity.GifEntity

interface ILocalGifDataSource {
    suspend fun deleteGif(id: String)
    suspend fun getGif(id: String): GifEntity
}