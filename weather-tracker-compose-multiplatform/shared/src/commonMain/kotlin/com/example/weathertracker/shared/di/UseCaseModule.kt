package com.example.weathertracker.shared.di

import com.example.weathertracker.shared.domain.usecase.GetCurrentWeatherFlowUseCase
import com.example.weathertracker.shared.domain.usecase.GetCurrentWeatherFlowUseCaseImpl
import com.example.weathertracker.shared.domain.usecase.GetDailyForecastFlowUseCase
import com.example.weathertracker.shared.domain.usecase.GetDailyForecastFlowUseCaseImpl
import com.example.weathertracker.shared.domain.usecase.GetTempSensingSystemFlowUseCase
import com.example.weathertracker.shared.domain.usecase.GetTempSensingSystemFlowUseCaseImpl
import com.example.weathertracker.shared.domain.usecase.GetTempSensingSystemUseCase
import com.example.weathertracker.shared.domain.usecase.GetTempSensingSystemUseCaseImpl
import com.example.weathertracker.shared.domain.usecase.SetTempSensingSystemUseCase
import com.example.weathertracker.shared.domain.usecase.SetTempSensingSystemUseCaseImpl
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
    factoryOf(::GetTempSensingSystemFlowUseCaseImpl) { bind<GetTempSensingSystemFlowUseCase>() }
    factoryOf(::SetTempSensingSystemUseCaseImpl) { bind<SetTempSensingSystemUseCase>() }
    factoryOf(::GetTempSensingSystemUseCaseImpl) { bind<GetTempSensingSystemUseCase>() }
}
