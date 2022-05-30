package com.example.testtask.data.datasource.db

import com.example.testtask.data.db.dao.GifDao
import com.example.testtask.data.db.entity.GifEntity
import com.example.testtask.domain.datasource.ILocalGifDataSource

class LocalGifsDataSource(
    private val gifsDao: GifDao,
) : ILocalGifDataSource {
    override suspend fun deleteGif(id: String) {
        gifsDao.deleteGif(id)
    }

    override suspend fun getGif(id: String): GifEntity {
        return gifsDao.getGif(id)
    }
}
