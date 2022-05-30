package com.example.testtask.di

import com.example.testtask.data.repository.GifsRepository
import com.example.testtask.domain.repository.IGifsRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single<IGifsRepository> {
        GifsRepository(
            remoteDataSource = get(),
            localDataSource = get(),
            mapper = get(named("GIFS"))
        )
    }
}
