package com.example.testtask.data.handler

import com.example.testtask.domain.exception.RequestException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandlerImpl(
    private val errorMessageProvider: ErrorMessageProvider,
    private val networkUtilsProvider: NetworkUtilsProvider
) : ErrorHandler {

    override fun getError(cause: Throwable): RequestException =
        when (cause) {
            is SocketTimeoutException -> RequestException.Network(
                description = errorMessageProvider.socketTimeoutError(),
                type = RequestException.Network.NetworkType.TIMEOUT
            )
            is UnknownHostException,
            is ConnectException -> cause.mapToRequestException()
            is HttpException -> when (cause.code()) {
                429 -> RequestException.TooManyRequestException(
                    description = errorMessageProvider.requestLimitError(),
                    throwable = cause
                )
                else -> cause.getDefaultError()
            }
            else -> cause.getDefaultError()
        }

    private fun Throwable.getDefaultError() = RequestException.DefaultException(
        description = errorMessageProvider.defaultError(),
        throwable = this
    )

    private fun Throwable.mapToRequestException(): RequestException {
        return if (!networkUtilsProvider.isNetworkAvailable()) {
            RequestException.Network(
                description = errorMessageProvider.noInternetError(),
                type = RequestException.Network.NetworkType.NO_INTERNET
            )
        } else {
            this.getDefaultError()
        }
    }
}
