package com.example.weathertracker.domain.repository

import com.example.weathertracker.domain.model.WeatherEntity
import com.example.weathertracker.domain.model.DailyForecastEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getRemoteCurrentWeather(): WeatherEntity
    fun getCashedWeather(): Flow<WeatherEntity?>
    suspend fun cacheWeather(weather: WeatherEntity)

    suspend fun getRemoteDailyForecast(): List<DailyForecastEntity>
    fun getCashedDailyForecast(): Flow<List<DailyForecastEntity>?>
    suspend fun cacheDailyForecast(forecast: List<DailyForecastEntity>)
} 