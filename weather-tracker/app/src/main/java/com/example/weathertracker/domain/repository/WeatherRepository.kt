package com.example.weathertracker.domain.repository

import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.domain.model.DailyForecast
import com.example.weathertracker.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(location: Location): Weather
    suspend fun getDailyForecast(location: Location): List<DailyForecast>
    fun getLastKnownWeather(): Flow<Weather?>
    fun getDailyForecastFromCache(): Flow<List<DailyForecast>>
    suspend fun updateWeatherCache(weather: Weather)
    suspend fun updateForecastCache(forecast: List<DailyForecast>)
} 