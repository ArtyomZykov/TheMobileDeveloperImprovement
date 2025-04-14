package com.example.weathertracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getUpdateInterval(): Flow<Long>
    suspend fun setUpdateInterval(intervalMinutes: Long)
}
