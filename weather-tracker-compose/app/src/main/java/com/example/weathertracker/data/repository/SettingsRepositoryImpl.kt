package com.example.weathertracker.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weathertracker.domain.repository.SettingsRepository
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

    private val updateIntervalKey = longPreferencesKey("update_interval")

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