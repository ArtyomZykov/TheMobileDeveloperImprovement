package com.example.weathertracker.domain.repository

import com.example.weathertracker.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getCurrentLocation(): Location
    fun getLastKnownLocation(): Flow<Location?>
} 