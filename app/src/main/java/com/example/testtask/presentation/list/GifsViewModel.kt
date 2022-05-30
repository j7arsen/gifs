package com.example.testtask.presentation.list

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.testtask.core.base.BaseViewModel
import com.example.testtask.domain.interactor.GifsInteractor
import com.example.testtask.domain.model.Result
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class GifsViewModel(
    private val interactor: GifsInteractor,
) : BaseViewModel() {

    private val _deleteGifState = MutableStateFlow<GifDeleteState>(GifDeleteState.Default)
    val deleteGifState = _deleteGifState.asLiveData()

    private val searchQueryFlow = MutableStateFlow("")

    @OptIn(FlowPreview::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val gifs = searchQueryFlow
        .debounce(SEARCH_DEBOUNCE)
        .flatMapLatest { searchQuery ->
            interactor.getGifs(searchQuery)
        }.cachedIn(viewModelScope)

    fun searchGifs(query: String) {
        searchQueryFlow.tryEmit(query)
    }

    fun onDeleteGifClicked(gifId: String?) {
        viewModelScope.launch {
            interactor.handleDeleteGif(gifId).collect { result ->
                when (result) {
                    Result.Loading -> _deleteGifState.emit(GifDeleteState.Loading)
                    is Result.Error -> _deleteGifState.emit(GifDeleteState.Error(result.exception))
                    is Result.Success -> _deleteGifState.emit(GifDeleteState.Success(result.data))
                }
            }
        }
    }

    sealed class GifDeleteState {
        data class Success(val message: String) : GifDeleteState()
        data class Error(val message: String) : GifDeleteState()
        object Loading : GifDeleteState()
        object Default : GifDeleteState()
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 300L
    }
}
