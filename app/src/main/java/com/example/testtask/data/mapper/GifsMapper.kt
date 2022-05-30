package com.example.testtask.data.mapper

import com.example.testtask.data.api.response.GifDataResponse
import com.example.testtask.data.db.entity.GifEntity
import com.example.testtask.domain.model.GifModel

class GifsMapper : IMapper<GifDataResponse, GifEntity, GifModel> {

    override fun mapEntityToModel(entity: GifDataResponse): GifModel {
        return GifModel(
            id = entity.id.orEmpty(),
            title = entity.title.orEmpty(),
            createDate = entity.importDatetime.orEmpty(),
            url = entity.images?.original?.url.orEmpty()
        )
    }

    override fun mapModelToEntity(model: GifModel): GifDataResponse {
        return GifDataResponse()
    }

    override fun mapEntityToCache(entity: GifDataResponse): GifEntity {
        return GifEntity(
            gifId = entity.id.orEmpty(),
            title = entity.title.orEmpty(),
            url = entity.images?.original?.url.orEmpty(),
            createDate = entity.importDatetime.orEmpty()
        )
    }

    override fun mapCacheToModel(cache: GifEntity): GifModel {
        return GifModel(
            id = cache.gifId,
            title = cache.title,
            createDate = cache.createDate,
            url = cache.url
        )
    }
}
