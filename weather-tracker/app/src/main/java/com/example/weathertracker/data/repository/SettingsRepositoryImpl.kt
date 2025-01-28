package com.example.weathertracker.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weathertracker.domain.repository.SettingsRepository
import com.example.weathertracker.domain.repository.TemperatureUnit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val temperatureUnitKey = stringPreferencesKey("temperature_unit")
    private val updateIntervalKey = longPreferencesKey("update_interval")

    override fun getTemperatureUnit(): Flow<TemperatureUnit> {
        return context.dataStore.data.map { preferences ->
            preferences[temperatureUnitKey]?.let { TemperatureUnit.valueOf(it) }
                ?: TemperatureUnit.CELSIUS
        }
    }

    override suspend fun setTemperatureUnit(unit: TemperatureUnit) {
        context.dataStore.edit { preferences ->
            preferences[temperatureUnitKey] = unit.name
        }
    }

    override fun getUpdateInterval(): Flow<Long> {
        return context.dataStore.data.map { preferences ->
            preferences[updateIntervalKey] ?: DEFAULT_UPDATE_INTERVAL
        }
    }

    override suspend fun setUpdateInterval(intervalMinutes: Long) {
        context.dataStore.edit { preferences ->
            preferences[updateIntervalKey] = intervalMinutes
        }
    }

    companion object {
        private const val DEFAULT_UPDATE_INTERVAL = 30L // 30 minutes
    }
} 