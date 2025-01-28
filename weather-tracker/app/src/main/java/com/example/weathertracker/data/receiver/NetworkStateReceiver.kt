package com.example.weathertracker.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.WorkManager
import com.example.weathertracker.work.WeatherUpdateWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NetworkStateReceiver : BroadcastReceiver() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val connectivityManager = 
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false

            if (isConnected) {
                // Запускаем обновление при восстановлении подключения
                workManager.enqueueUniquePeriodicWork(
                    WeatherUpdateWorker.WORK_NAME,
                    androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                    androidx.work.PeriodicWorkRequestBuilder<WeatherUpdateWorker>(
                        15, // Минимальный интервал
                        java.util.concurrent.TimeUnit.MINUTES
                    ).build()
                )
            }
        }
    }
} 