package com.example.weathertracker.shared.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherDbEntity(
    @PrimaryKey
    val id: Long,
    val kelvinTemperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String,
    val timestamp: Long
)
