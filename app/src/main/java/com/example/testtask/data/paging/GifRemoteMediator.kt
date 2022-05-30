package com.example.testtask.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.testtask.BuildConfig
import com.example.testtask.data.api.GifService
import com.example.testtask.data.api.response.GifDataResponse
import com.example.testtask.data.api.response.GifsResponse
import com.example.testtask.data.api.utils.RequestParams
import com.example.testtask.data.db.AppDatabase
import com.example.testtask.data.db.entity.GifEntity
import com.example.testtask.data.db.entity.GifRemoteKeysEntity
import com.example.testtask.data.handler.ErrorHandler
import com.example.testtask.data.mapper.IMapper
import com.example.testtask.domain.model.GifModel

@OptIn(ExperimentalPagingApi::class)
class GifRemoteMediator(
    private val skipRefresh: Boolean = false,
    private val searchQuery: String,
    private val gifsService: GifService,
    private val gifsDb: AppDatabase,
    private val mapper: IMapper<GifDataResponse, GifEntity, GifModel>,
    private val errorHandler: ErrorHandler
) : RemoteMediator<Int, GifEntity>() {

    private val gifDao = gifsDb.gifDao()
    private val gifRemoteKeysDao = gifsDb.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return if (skipRefresh) {
            InitializeAction.SKIP_INITIAL_REFRESH // Cached data is up-to-date, so there is no need to re-fetch from network.
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH // Need to refresh cached data from network
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifEntity>
    ): MediatorResult {
        return try {
            val page = when (val pageData = getPage(loadType, state)) {
                is MediatorResult.Success -> return pageData
                else -> pageData as Int
            }
            val pageSize = state.config.pageSize
            val response = gifsService.getGifs(
                RequestParams.getGifsParams(
                    apiKey = BuildConfig.API_KEY,
                    searchQuery = searchQuery,
                    limit = pageSize,
                    offset = page * pageSize,
                )
            )
            var endOfPaginationReached = false
            if (response.isSuccessful) {
                val responseData: GifsResponse? = response.body()
                endOfPaginationReached = responseData?.gifsData.isNullOrEmpty()
                responseData?.let { data ->
                    gifsDb.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            gifDao.deleteAllGifs()
                            gifRemoteKeysDao.deleteAllGifRemoteKeys()
                        }
                        val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                        val nextKey = if (endOfPaginationReached) null else page + 1

                        val dataToCache = data.gifsData.map(mapper::mapEntityToCache)

                        val keys = dataToCache.map { gif ->
                            GifRemoteKeysEntity(
                                id = gif.gifId,
                                prevPage = prevKey,
                                nextPage = nextKey,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        gifRemoteKeysDao.addAllGifRemoteKeys(gifRemoteKeys = keys)
                        gifDao.addGifs(gifs = dataToCache)
                    }
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(errorHandler.getError(e))
        }
    }

    private suspend fun getPage(loadType: LoadType, state: PagingState<Int, GifEntity>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GifEntity>,
    ): GifRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.gifId?.let { id ->
                gifRemoteKeysDao.getGifRemoteKeys(gifId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, GifEntity>,
    ): GifRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { gif ->
                gifRemoteKeysDao.getGifRemoteKeys(gifId = gif.gifId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, GifEntity>,
    ): GifRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { gif ->
                gifRemoteKeysDao.getGifRemoteKeys(gifId = gif.gifId)
            }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }
}
