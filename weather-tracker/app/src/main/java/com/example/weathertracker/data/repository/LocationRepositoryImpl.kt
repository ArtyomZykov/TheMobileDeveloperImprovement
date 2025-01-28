package com.example.weathertracker.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.example.weathertracker.domain.model.Location as DomainLocation
import com.example.weathertracker.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationRepository {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val lastKnownLocationFlow = MutableStateFlow<DomainLocation?>(null)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): DomainLocation = withContext(Dispatchers.IO) {
        val location = Tasks.await(fusedLocationClient.lastLocation)
        location?.let {
            DomainLocation(it.latitude, it.longitude)
        } ?: throw IllegalStateException("Location not available")
    }

    override fun getLastKnownLocation(): Flow<DomainLocation?> = lastKnownLocationFlow

    private fun Location.toDomainLocation() = DomainLocation(
        latitude = latitude,
        longitude = longitude
    )
} 