package com.example.testtask.data.handler

import com.example.testtask.domain.exception.RequestException

interface ErrorHandler {
    fun getError(cause: Throwable): RequestException
}