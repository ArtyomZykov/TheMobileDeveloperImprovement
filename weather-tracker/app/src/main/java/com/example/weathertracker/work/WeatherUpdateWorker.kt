package com.example.weathertracker.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weathertracker.domain.repository.SettingsRepository
import com.example.weathertracker.domain.repository.WeatherRepository
import com.example.weathertracker.util.Notification
import com.example.weathertracker.util.Notification.showNotification
import com.example.weathertracker.util.Timestamp
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

            val updateTime = Timestamp.convertUnixTimestampToDate(weather.timestamp)
            applicationContext.showNotification(
                title = "Current Weather",
                message = "Temperature: ${weather.temperature}°C, " +
                        "time: $updateTime"
            )
            Log.d("WeatherUpdateWorker - success", "time: $updateTime")

            Result.success()
        } catch (e: Exception) {
            Log.e("WeatherUpdateWorker - error", e.message.toString())
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "weather_update_work"
    }
}