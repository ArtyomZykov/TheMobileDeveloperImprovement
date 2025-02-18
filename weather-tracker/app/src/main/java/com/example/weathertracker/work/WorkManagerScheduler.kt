package com.example.weathertracker.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weathertracker.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkManagerScheduler @Inject constructor(
    private val context: Context,
    private val settingsRepository: SettingsRepository
) {

    suspend fun scheduleWeatherUpdates() {
        val updateInterval = settingsRepository.getUpdateInterval().first()
        
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val weatherUpdateRequest = PeriodicWorkRequestBuilder<WeatherUpdateWorker>(
            updateInterval,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WeatherUpdateWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            weatherUpdateRequest
        )
    }

    fun cancelWeatherUpdates() {
        WorkManager.getInstance(context).cancelUniqueWork(WeatherUpdateWorker.WORK_NAME)
    }
} 