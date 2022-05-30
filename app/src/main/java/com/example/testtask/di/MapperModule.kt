package com.example.testtask.di

import com.example.testtask.data.api.response.GifDataResponse
import com.example.testtask.data.db.entity.GifEntity
import com.example.testtask.data.mapper.GifsMapper
import com.example.testtask.data.mapper.IMapper
import com.example.testtask.domain.model.GifModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {

    single<IMapper<GifDataResponse, GifEntity, GifModel>>(named("GIFS")) { GifsMapper() }
}
