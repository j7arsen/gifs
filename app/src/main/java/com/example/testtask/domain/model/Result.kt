package com.example.testtask.domain.model

sealed class Result<out MODEL> {
    data class Success<MODEL>(val data: MODEL) : Result<MODEL>()
    data class Error(val exception: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
