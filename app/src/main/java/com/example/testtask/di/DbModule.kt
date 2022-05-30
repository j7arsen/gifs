package com.example.testtask.di

import com.example.testtask.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getInstance(androidContext()) }
    single { get<AppDatabase>().gifDao() }
    single { get<AppDatabase>().remoteKeysDao() }
}