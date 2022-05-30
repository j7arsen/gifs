package com.example.testtask.domain.interactor

import com.example.testtask.domain.interactor.usecase.GetGifsUseCase

class GifDetailInteractor(
    private val getGifsUseCase: GetGifsUseCase
) {

    fun getGifs(searchQuery: String, idFrom: String?) = getGifsUseCase.invoke(
        searchQuery = searchQuery,
        idFrom = idFrom,
        isSkipRefresh = true
    )
}
