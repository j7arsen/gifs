package com.example.testtask.data.repository

import androidx.paging.PagingData
import com.example.testtask.data.api.response.GifDataResponse
import com.example.testtask.data.db.entity.GifEntity
import com.example.testtask.data.mapper.IMapper
import com.example.testtask.domain.datasource.ILocalGifDataSource
import com.example.testtask.domain.datasource.IRestGifsDatasource
import com.example.testtask.domain.model.GifModel
import com.example.testtask.domain.repository.IGifsRepository
import kotlinx.coroutines.flow.Flow

class GifsRepository(
    private val remoteDataSource: IRestGifsDatasource,
    private val localDataSource: ILocalGifDataSource,
    private val mapper: IMapper<GifDataResponse, GifEntity, GifModel>
) : IGifsRepository {

    override fun getGifs(
        searchQuery: String,
        idFrom: String?,
        isSkipRefresh: Boolean
    ): Flow<PagingData<GifModel>> {
        return remoteDataSource.getGifs(searchQuery, idFrom, isSkipRefresh)
    }

    override suspend fun getGif(gifId: String): GifModel {
        return localDataSource.getGif(gifId).let(mapper::mapCacheToModel)
    }

    override suspend fun deleteGif(id: String) {
        localDataSource.deleteGif(id)
    }
}
