package com.example.testtask.di

import com.example.testtask.presentation.app.AppViewModel
import com.example.testtask.presentation.detail.GifDetailViewModel
import com.example.testtask.presentation.list.GifsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        AppViewModel()
    }

    viewModel {
        GifsViewModel(get())
    }

    viewModel { (query: String, idFrom: String?) ->
        GifDetailViewModel(
            query = query,
            interactor = get(),
            idFrom = idFrom
        )
    }
}
