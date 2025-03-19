package com.example.weathertracker.data.db.dao

import android.database.Cursor
import androidx.room.*
import com.example.weathertracker.data.db.entity.WeatherDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather ORDER BY timestamp DESC LIMIT 1")
    fun getLastWeather(): Flow<WeatherDbEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherDbEntity)

    @Query("DELETE FROM weather")
    suspend fun deleteAllWeather()

    @Query("SELECT * FROM weather ORDER BY timestamp DESC")
    fun getWeatherCursor(): Cursor

    @Query("SELECT * FROM weather WHERE id = :id")
    fun getWeatherByIdCursor(id: Long): Cursor
} 