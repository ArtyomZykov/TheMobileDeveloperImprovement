package com.example.weathertracker.shared.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathertracker.shared.data.db.entity.ForecastDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast ORDER BY date ASC")
    fun getAllForecasts(): Flow<List<ForecastDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(forecasts: List<ForecastDbEntity>)
}
