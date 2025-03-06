package com.example.weathertracker.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weathertracker.domain.repository.SettingsRepository
import com.example.weathertracker.domain.repository.WeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WeatherUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val weather = weatherRepository.getRemoteCurrentWeather()
            val forecast = weatherRepository.getRemoteDailyForecast()
            
            weatherRepository.cacheWeather(weather)
            weatherRepository.cacheDailyForecast(forecast)
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "weather_update_work"
    }
}