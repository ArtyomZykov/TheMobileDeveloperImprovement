package com.example.weathertracker.shared.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathertracker.shared.data.db.entity.WeatherDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather ORDER BY timestamp DESC LIMIT 1")
    fun getLastWeather(): Flow<WeatherDbEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherDbEntity)
}
