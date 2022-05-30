package com.example.testtask.di

import com.example.testtask.domain.interactor.GifDetailInteractor
import com.example.testtask.domain.interactor.GifsInteractor
import com.example.testtask.domain.interactor.usecase.GetGifsUseCase
import org.koin.dsl.module

val interactorsModule = module {

    single {
        GifsInteractor(
            gifsRepository = get(),
            getGifsUseCase = get(),
            messageProvider = get()
        )
    }

    single {
        GifDetailInteractor(
            getGifsUseCase = get()
        )
    }

    single { GetGifsUseCase(get()) }
}
