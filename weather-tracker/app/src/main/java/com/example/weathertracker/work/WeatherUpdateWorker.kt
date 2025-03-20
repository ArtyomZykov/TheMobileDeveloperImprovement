package com.example.weathertracker.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weathertracker.domain.repository.SettingsRepository
import com.example.weathertracker.domain.repository.WeatherRepository
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

            showNotification(
                title = "Current Weather",
                message = "Temperature: ${weather.temperature}°C, " +
                        "time: ${Timestamp.convertUnixTimestampToDate(weather.timestamp)}"
            )

            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val channelId = "weather_update_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Weather Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for weather update notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    companion object {
        const val WORK_NAME = "weather_update_work"
    }
}