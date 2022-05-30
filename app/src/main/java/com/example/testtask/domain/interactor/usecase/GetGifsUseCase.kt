package com.example.testtask.domain.interactor.usecase

import androidx.paging.PagingData
import com.example.testtask.domain.model.GifModel
import com.example.testtask.domain.repository.IGifsRepository
import kotlinx.coroutines.flow.Flow

class GetGifsUseCase(
    private val gifsRepository: IGifsRepository
) {

    operator fun invoke(
        searchQuery: String,
        idFrom: String? = null,
        isSkipRefresh: Boolean = false
    ): Flow<PagingData<GifModel>> {
        val query = searchQuery.takeIf(String::isNotBlank) ?: DEFAULT_SEARCH_QUERY
        return gifsRepository.getGifs(
            searchQuery = query,
            idFrom = idFrom,
            isSkipRefresh
        )
    }

    companion object {
        const val DEFAULT_SEARCH_QUERY = "cheeseburgers"
    }
}
