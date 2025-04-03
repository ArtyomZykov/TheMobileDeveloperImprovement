package com.example.weathertracker.shared.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.example.weathertracker.shared.data.db.dao.ForecastDao
import com.example.weathertracker.shared.data.db.dao.WeatherDao
import com.example.weathertracker.shared.data.db.entity.ForecastDbEntity
import com.example.weathertracker.shared.data.db.entity.WeatherDbEntity

@Database(
    entities = [WeatherDbEntity::class, ForecastDbEntity::class],
    version = 1
)
@ConstructedBy(WeatherDatabaseConstructor::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
    abstract fun forecastDao(): ForecastDao

    companion object {
        const val DATABASE_NAME = "weather_database.db"
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object WeatherDatabaseConstructor : RoomDatabaseConstructor<WeatherDatabase> {
    override fun initialize(): WeatherDatabase
}

internal fun getWeatherDao(appDatabase: WeatherDatabase) = appDatabase.weatherDao()
internal fun getForecastDao(appDatabase: WeatherDatabase) = appDatabase.forecastDao()
