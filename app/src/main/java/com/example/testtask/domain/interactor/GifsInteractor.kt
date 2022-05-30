package com.example.testtask.domain.interactor

import com.example.testtask.domain.ResourceProvider
import com.example.testtask.domain.interactor.usecase.GetGifsUseCase
import com.example.testtask.domain.model.Result
import com.example.testtask.domain.repository.IGifsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GifsInteractor(
    private val gifsRepository: IGifsRepository,
    private val getGifsUseCase: GetGifsUseCase,
    private val messageProvider: ResourceProvider
) {

    fun getGifs(searchQuery: String) = getGifsUseCase.invoke(searchQuery)

    suspend fun handleDeleteGif(id: String?) = flow {
        emit(Result.Loading)
        id.takeIf { !it.isNullOrBlank() }?.let {
            gifsRepository.deleteGif(id = it)
            emit(Result.Success(messageProvider.messageDeleteGifSuccess()))
        } ?: emit(Result.Error(messageProvider.messageDeleteGifError()))
    }.catch {
        emit(Result.Error(messageProvider.messageDeleteGifError()))
    }
}
