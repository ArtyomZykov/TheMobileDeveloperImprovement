package com.example.weathertracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weathertracker.data.db.dao.ForecastDao
import com.example.weathertracker.data.db.dao.WeatherDao
import com.example.weathertracker.data.db.entity.ForecastEntity
import com.example.weathertracker.data.db.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class, ForecastEntity::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun forecastDao(): ForecastDao
} 