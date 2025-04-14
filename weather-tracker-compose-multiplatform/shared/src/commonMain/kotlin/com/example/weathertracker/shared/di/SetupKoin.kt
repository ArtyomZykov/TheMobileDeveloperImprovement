package com.example.weathertracker.shared.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun setupIosKoin() {
    startKoin {
        setupKoin()
    }
}

fun KoinApplication.setupKoin() {
    modules(
        httpClientModule,
        dataStoreModule,
        roomDatabaseModule,
        roomDaoModule,
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )
}
