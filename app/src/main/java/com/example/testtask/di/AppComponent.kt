package com.example.testtask.di

import org.koin.core.module.Module

val appComponent: List<Module> = listOf(
    navigationModule,
    netWorkModule,
    databaseModule,
    repositoryModule,
    viewModelModule,
    interactorsModule,
    databaseModule,
    mapperModule,
    dataSourceModule,
    utilsModule
)
