package com.example.weathertracker.shared.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weathertracker.shared.data.db.WeatherDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSFileManager

actual val roomDatabaseModule: Module = module {
    single<RoomDatabase.Builder<WeatherDatabase>> {
        getDatabaseBuilder()
    }
    
    single<WeatherDatabase> {
        get<RoomDatabase.Builder<WeatherDatabase>>().build()
    }
}

private fun getDatabaseBuilder(): RoomDatabase.Builder<WeatherDatabase> {
    val dbFilePath = documentDirectory() + "/" + WeatherDatabase.DATABASE_NAME
    return Room.databaseBuilder<WeatherDatabase>(name = dbFilePath)
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
