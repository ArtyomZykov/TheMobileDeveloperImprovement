package com.example.weathertracker.shared.di

import com.example.weathertracker.shared.domain.usecase.GetCurrentWeatherFlowUseCase
import com.example.weathertracker.shared.domain.usecase.GetCurrentWeatherFlowUseCaseImpl
import com.example.weathertracker.shared.domain.usecase.GetDailyForecastFlowUseCase
import com.example.weathertracker.shared.domain.usecase.GetDailyForecastFlowUseCaseImpl
import com.example.weathertracker.shared.domain.usecase.SyncCurrentWeatherUseCase
import com.example.weathertracker.shared.domain.usecase.SyncCurrentWeatherUseCaseImpl
import com.example.weathertracker.shared.domain.usecase.SyncDailyForecastUseCase
import com.example.weathertracker.shared.domain.usecase.SyncDailyForecastUseCaseImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCaseModule: Module = module {

    factoryOf(::GetCurrentWeatherFlowUseCaseImpl) { bind<GetCurrentWeatherFlowUseCase>() }
    factoryOf(::GetDailyForecastFlowUseCaseImpl) { bind<GetDailyForecastFlowUseCase>() }
    factoryOf(::SyncCurrentWeatherUseCaseImpl) { bind<SyncCurrentWeatherUseCase>() }
    factoryOf(::SyncDailyForecastUseCaseImpl) { bind<SyncDailyForecastUseCase>() }
}
