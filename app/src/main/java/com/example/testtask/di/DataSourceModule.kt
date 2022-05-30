package com.example.testtask.di

import com.example.testtask.data.datasource.db.LocalGifsDataSource
import com.example.testtask.data.datasource.rest.RestGifsDataSource
import com.example.testtask.domain.datasource.IRestGifsDatasource
import com.example.testtask.domain.datasource.ILocalGifDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {
    single<IRestGifsDatasource> {
        RestGifsDataSource(
            gifsService = get(),
            errorHandler = get(),
            gifDao = get(),
            appDatabase = get(),
            mapper = get(named("GIFS")),

        )
    }
    single<ILocalGifDataSource> {
        LocalGifsDataSource(get())
    }
}
