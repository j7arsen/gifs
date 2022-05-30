package com.example.testtask.presentation.detail

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.testtask.core.base.BaseViewModel
import com.example.testtask.domain.interactor.GifDetailInteractor

class GifDetailViewModel(
    query: String,
    idFrom: String?,
    interactor: GifDetailInteractor,
) : BaseViewModel() {

    val gifs = interactor
        .getGifs(query, idFrom)
        .cachedIn(viewModelScope)
}
