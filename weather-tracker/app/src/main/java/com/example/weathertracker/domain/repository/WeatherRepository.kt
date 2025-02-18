package com.example.weathertracker.domain.repository

import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.domain.model.DailyForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getRemoteCurrentWeather(): Weather
    fun getCashedWeather(): Flow<Weather?>
    suspend fun cacheWeather(weather: Weather)

    suspend fun getDailyForecast(): List<DailyForecast>
    fun getDailyForecastFromCache(): Flow<List<DailyForecast>>
    suspend fun updateForecastCache(forecast: List<DailyForecast>)
} 