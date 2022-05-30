package com.example.testtask.presentation.app

import android.app.Application
import com.example.testtask.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(appComponent)
        }
    }
}
