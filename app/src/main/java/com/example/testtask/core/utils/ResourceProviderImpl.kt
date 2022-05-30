package com.example.testtask.core.utils

import android.content.Context
import com.example.testtask.R
import com.example.testtask.domain.ResourceProvider

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun messageDeleteGifSuccess() = context.getString(R.string.gif_delete_success)

    override fun messageDeleteGifError() = context.getString(R.string.gif_delete_error)
}
