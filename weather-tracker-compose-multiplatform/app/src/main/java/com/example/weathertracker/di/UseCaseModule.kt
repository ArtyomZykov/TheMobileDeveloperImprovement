package com.example.weathertracker.di

import com.example.weathertracker.domain.usecase.GetCurrentWeatherFlowUseCase
import com.example.weathertracker.domain.usecase.GetCurrentWeatherFlowUseCaseImpl
import com.example.weathertracker.domain.usecase.GetDailyForecastFlowUseCase
import com.example.weathertracker.domain.usecase.GetDailyForecastFlowUseCaseImpl
import com.example.weathertracker.domain.usecase.SyncCurrentWeatherUseCase
import com.example.weathertracker.domain.usecase.SyncCurrentWeatherUseCaseImpl
import com.example.weathertracker.domain.usecase.SyncDailyForecastUseCase
import com.example.weathertracker.domain.usecase.SyncDailyForecastUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindGetCurrentWeatherFlowUseCase(
        useCase: GetCurrentWeatherFlowUseCaseImpl
    ): GetCurrentWeatherFlowUseCase

    @Binds
    @Singleton
    abstract fun bindGetDailyForecastFlowUseCase(
        useCase: GetDailyForecastFlowUseCaseImpl
    ): GetDailyForecastFlowUseCase

    @Binds
    @Singleton
    abstract fun bindSyncCurrentWeatherUseCase(
        useCase: SyncCurrentWeatherUseCaseImpl
    ): SyncCurrentWeatherUseCase

    @Binds
    @Singleton
    abstract fun bindSyncDailyForecastUseCase(
        useCase: SyncDailyForecastUseCaseImpl
    ): SyncDailyForecastUseCase
} 