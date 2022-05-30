package com.example.testtask.data.handler

interface ErrorMessageProvider {
    fun socketTimeoutError(): String
    fun noInternetError(): String
    fun defaultError(): String
    fun requestLimitError(): String
}