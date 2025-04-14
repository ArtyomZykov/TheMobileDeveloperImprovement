package com.example.weathertracker.shared.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.module.Module

internal expect val dataStoreModule: Module

internal object DataStoreUtils {

    internal const val DATA_STORE_FILE_NAME = "weather_tracker.preferences_pb"

    fun createDataStore(producePath: () -> String): DataStore<Preferences> =
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { producePath().toPath() }
        )
}
