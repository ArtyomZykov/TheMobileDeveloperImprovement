package com.example.weathertracker.shared.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.weathertracker.shared.domain.model.TemperatureSensingSystemEntity
import com.example.weathertracker.shared.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    private val updateIntervalKey = stringPreferencesKey("temperature_sensing_system")

    override suspend fun getTemperatureSensingSystemFlow(): Flow<TemperatureSensingSystemEntity> {
        return dataStore.data.map { preferences ->
            val value = preferences[updateIntervalKey] ?: DEFAULT_TEMPERATURE_SENSING_SYSTEM
            TemperatureSensingSystemEntity.fromKey(value.toString())
        }
    }

    override suspend fun getTemperatureSensingSystem(): TemperatureSensingSystemEntity {
        val stringByKey: String = dataStore.updateData { it }[updateIntervalKey]
            ?: return DEFAULT_TEMPERATURE_SENSING_SYSTEM
        return TemperatureSensingSystemEntity.fromKey(stringByKey)
    }

    override suspend fun setTemperatureSensingSystem(
        temperatureSensingSystem: TemperatureSensingSystemEntity,
    ) {
        dataStore.edit { preferences ->
            preferences[updateIntervalKey] = temperatureSensingSystem.toKey()
        }
    }

    companion object {
        private val DEFAULT_TEMPERATURE_SENSING_SYSTEM = TemperatureSensingSystemEntity.Celsius
    }
}
