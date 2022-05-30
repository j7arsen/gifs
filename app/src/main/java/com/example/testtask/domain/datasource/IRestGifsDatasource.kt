package com.example.testtask.domain.datasource

import androidx.paging.PagingData
import com.example.testtask.domain.model.GifModel
import kotlinx.coroutines.flow.Flow

interface IRestGifsDatasource {
    fun getGifs(
        searchQuery: String,
        idFrom: String? = null,
        isSkipRefresh: Boolean = false
    ): Flow<PagingData<GifModel>>
}
