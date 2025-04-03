package com.example.weathertracker.shared.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getUpdateInterval(): Flow<Long>
    suspend fun setUpdateInterval(intervalMinutes: Long)
}
