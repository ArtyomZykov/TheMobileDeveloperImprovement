package com.example.weathertracker.data.api

import com.example.weathertracker.data.api.model.CurrentWeatherResponse
import com.example.weathertracker.data.api.model.ForecastResponse
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
    ): CurrentWeatherResponse

    @GET("forecast")
    suspend fun getDailyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("cnt") count: Int = 40,
    ): ForecastResponse
} 