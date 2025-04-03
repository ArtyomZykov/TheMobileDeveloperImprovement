package com.example.weathertracker.shared.domain.model

data class WeatherEntity(
    val id: Long,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String,
    val timestamp: Long,
)
