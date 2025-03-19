package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.repository.WeatherRepository
import javax.inject.Inject

/**
 * Use case for synchronizing the daily forecast.
 * @property weatherRepository The repository to fetch and cache daily forecast data.
 */
class SyncDailyForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    /**
     * Invokes the use case to fetch the daily forecast from a remote source and cache it.
     */
    suspend operator fun invoke() {
        val remoteDailyForecast = weatherRepository.getRemoteDailyForecast()
        weatherRepository.cacheDailyForecast(remoteDailyForecast)
    }
}
