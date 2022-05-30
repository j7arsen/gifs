package com.example.testtask.domain.repository

import androidx.paging.PagingData
import com.example.testtask.domain.model.GifModel
import kotlinx.coroutines.flow.Flow

interface IGifsRepository {
    fun getGifs(
        searchQuery: String,
        idFrom: String? = null,
        isSkipRefresh: Boolean = false
    ): Flow<PagingData<GifModel>>

    suspend fun getGif(gifId: String): GifModel
    suspend fun deleteGif(id: String)
}
