package com.example.weathertracker.shared.di

import com.example.weathertracker.shared.data.db.getForecastDao
import com.example.weathertracker.shared.data.db.getWeatherDao
import org.koin.core.module.Module
import org.koin.dsl.module

internal val roomDaoModule: Module = module {

    single { getWeatherDao(get()) }
    single { getForecastDao(get()) }
}
