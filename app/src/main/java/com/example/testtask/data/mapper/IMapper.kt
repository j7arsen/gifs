package com.example.testtask.data.mapper

interface IMapper<ENTITY, CACHE, MODEL> {

    fun mapEntityToModel(entity: ENTITY): MODEL

    fun mapModelToEntity(model: MODEL): ENTITY

    fun mapEntityToCache(entity: ENTITY): CACHE

    fun mapCacheToModel(cache: CACHE): MODEL
}
