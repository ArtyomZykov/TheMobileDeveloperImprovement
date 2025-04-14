package com.example.weathertracker.shared.domain.repository

import com.example.weathertracker.shared.domain.model.TemperatureSensingSystemEntity
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getTemperatureSensingSystemFlow(): Flow<TemperatureSensingSystemEntity>
    suspend fun getTemperatureSensingSystem(): TemperatureSensingSystemEntity
    suspend fun setTemperatureSensingSystem(temperatureSensingSystem: TemperatureSensingSystemEntity)
}
