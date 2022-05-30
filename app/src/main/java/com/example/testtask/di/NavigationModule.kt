package com.example.testtask.di

import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val navigationModule = module {
    val cicerone: Cicerone<Router> = Cicerone.create()
    single { cicerone.router }
    single { cicerone.navigatorHolder }
}
