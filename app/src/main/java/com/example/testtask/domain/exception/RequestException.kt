package com.example.testtask.domain.exception

sealed class RequestException(message: String) : RuntimeException(message) {
    data class DefaultException(val description: String, val throwable: Throwable) :
        RequestException(description)

    data class UnknownException(val description: String) : RequestException(description)
    data class TooManyRequestException(val description: String, val throwable: Throwable) :
        RequestException(description)

    data class Network(val description: String, val type: NetworkType) :
        RequestException(description) {
        enum class NetworkType {
            NO_INTERNET,
            TIMEOUT
        }
    }
}
