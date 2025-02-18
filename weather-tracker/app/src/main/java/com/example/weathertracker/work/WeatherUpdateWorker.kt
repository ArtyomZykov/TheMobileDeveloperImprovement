package com.example.weathertracker.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weathertracker.domain.repository.LocationRepository
import com.example.weathertracker.domain.repository.SettingsRepository
import com.example.weathertracker.domain.repository.WeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WeatherUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val settingsRepository: SettingsRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val location = locationRepository.getCurrentLocation()
            val weather = weatherRepository.getCurrentWeather(location)
            val forecast = weatherRepository.getDailyForecast(location)
            
            weatherRepository.updateWeatherCache(weather)
            weatherRepository.updateForecastCache(forecast)
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "weather_update_work"
    }
}