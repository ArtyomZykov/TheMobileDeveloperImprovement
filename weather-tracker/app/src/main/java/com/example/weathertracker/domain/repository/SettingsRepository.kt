package com.example.weathertracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getTemperatureUnit(): Flow<TemperatureUnit>
    suspend fun setTemperatureUnit(unit: TemperatureUnit)
    fun getUpdateInterval(): Flow<Long>
    suspend fun setUpdateInterval(intervalMinutes: Long)
}

enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT
} 