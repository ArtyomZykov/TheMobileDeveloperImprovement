package com.example.weathertracker.data.db.dao

import android.database.Cursor
import androidx.room.*
import com.example.weathertracker.data.db.entity.ForecastDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast ORDER BY date ASC")
    fun getAllForecasts(): Flow<List<ForecastDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(forecasts: List<ForecastDbEntity>)

    @Query("DELETE FROM forecast")
    suspend fun deleteAllForecasts()

    @Query("SELECT * FROM forecast ORDER BY date ASC")
    fun getForecastCursor(): Cursor

    @Query("SELECT * FROM forecast WHERE date = :date")
    fun getForecastByDateCursor(date: Long): Cursor
} 