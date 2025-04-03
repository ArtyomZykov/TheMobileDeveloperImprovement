package com.example.weathertracker.shared.di

import com.example.weathertracker.shared.data.repository.SettingsRepositoryImpl
import com.example.weathertracker.shared.data.repository.WeatherRepositoryImpl
import com.example.weathertracker.shared.domain.repository.SettingsRepository
import com.example.weathertracker.shared.domain.repository.WeatherRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val repositoryModule: Module = module {

    factoryOf(::WeatherRepositoryImpl) { bind<WeatherRepository>() }
    factoryOf(::SettingsRepositoryImpl) { bind<SettingsRepository>() }
}
