package com.example.weathertracker.shared.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.example.weathertracker.shared.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    private val updateIntervalKey = longPreferencesKey("update_interval")

    override suspend fun getUpdateInterval(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[updateIntervalKey] ?: DEFAULT_UPDATE_INTERVAL
        }
    }

    override suspend fun setUpdateInterval(intervalMinutes: Long) {
        dataStore.edit { preferences ->
            preferences[updateIntervalKey] = intervalMinutes
        }
    }

    companion object {
        private const val DEFAULT_UPDATE_INTERVAL = 30L // 30 minutes
    }
}
