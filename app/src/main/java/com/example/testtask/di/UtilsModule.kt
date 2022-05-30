package com.example.testtask.di

import com.example.testtask.core.utils.ErrorMessagesLocalization
import com.example.testtask.core.utils.NetworkUtilsProviderImpl
import com.example.testtask.core.utils.ResourceProviderImpl
import com.example.testtask.data.handler.ErrorHandler
import com.example.testtask.data.handler.ErrorHandlerImpl
import com.example.testtask.data.handler.ErrorMessageProvider
import com.example.testtask.data.handler.NetworkUtilsProvider
import com.example.testtask.domain.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {

    single<NetworkUtilsProvider> {
        NetworkUtilsProviderImpl(androidContext())
    }

    single<ErrorMessageProvider> { ErrorMessagesLocalization(androidContext()) }

    single<ErrorHandler> {
        ErrorHandlerImpl(
            errorMessageProvider = get(),
            networkUtilsProvider = get()
        )
    }

    single<ResourceProvider> {
        ResourceProviderImpl(androidContext())
    }
}
