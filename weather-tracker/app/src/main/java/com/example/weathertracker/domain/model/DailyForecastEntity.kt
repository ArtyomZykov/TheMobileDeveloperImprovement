package com.example.weathertracker.domain.model

data class DailyForecastEntity(
    val date: Long,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String,
)
