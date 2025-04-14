package com.example.weathertracker.shared.di

import org.koin.core.module.Module
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.weathertracker.shared.di.DataStoreUtils.DATA_STORE_FILE_NAME
import com.example.weathertracker.shared.di.DataStoreUtils.createDataStore
import org.koin.dsl.module

actual val dataStoreModule: Module = module {
    single<DataStore<Preferences>> { dataStore(get()) }
}

fun dataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath }
)
