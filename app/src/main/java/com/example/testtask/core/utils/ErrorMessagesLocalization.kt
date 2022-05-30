package com.example.testtask.core.utils

import android.content.Context
import com.example.testtask.R
import com.example.testtask.data.handler.ErrorMessageProvider

class ErrorMessagesLocalization(
    private val context: Context
) : ErrorMessageProvider {
    override fun socketTimeoutError(): String = context.getString(R.string.error_socket_timeout)

    override fun noInternetError(): String = context.getString(R.string.error_no_internet)

    override fun defaultError(): String = context.getString(R.string.error_default)

    override fun requestLimitError(): String = context.getString(R.string.error_request_limit)
}
