package com.example.testtask.data.datasource.rest

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.testtask.data.api.GifService
import com.example.testtask.data.api.response.GifDataResponse
import com.example.testtask.data.db.AppDatabase
import com.example.testtask.data.db.dao.GifDao
import com.example.testtask.data.db.entity.GifEntity
import com.example.testtask.data.handler.ErrorHandler
import com.example.testtask.data.mapper.IMapper
import com.example.testtask.data.paging.GifRemoteMediator
import com.example.testtask.domain.datasource.IRestGifsDatasource
import com.example.testtask.domain.model.GifModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RestGifsDataSource(
    private val gifsService: GifService,
    private val errorHandler: ErrorHandler,
    private val gifDao: GifDao,
    private val appDatabase: AppDatabase,
    private val mapper: IMapper<GifDataResponse, GifEntity, GifModel>,
) : IRestGifsDatasource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getGifs(
        searchQuery: String,
        idFrom: String?,
        isSkipRefresh: Boolean
    ): Flow<PagingData<GifModel>> {
        val pagingSourceFactory = { if (idFrom != null) gifDao.getAllGifsFrom(idFrom) else gifDao.getAllGifs() }
        return Pager(
            config = PagingConfig(
                pageSize = 15, enablePlaceholders = false, prefetchDistance = 20
            ),
            remoteMediator = GifRemoteMediator(
                skipRefresh = isSkipRefresh,
                searchQuery = searchQuery,
                gifsService = gifsService,
                gifsDb = appDatabase,
                mapper = mapper,
                errorHandler = errorHandler
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow.map { pagingData ->
            pagingData.map { gif -> mapper.mapCacheToModel(gif) }
        }
    }
}
