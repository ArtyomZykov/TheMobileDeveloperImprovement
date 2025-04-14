package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.repository.WeatherRepository
import javax.inject.Inject

/**
 * Use case for synchronizing the current weather.
 * @property weatherRepository The repository to fetch and cache weather data.
 */
class SyncCurrentWeatherUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : SyncCurrentWeatherUseCase {

    /**
     * Invokes the use case to fetch the current weather from a remote source and cache it.
     */
    override suspend operator fun invoke() {
        val remoteWeather = weatherRepository.getRemoteCurrentWeather()
        weatherRepository.cacheWeather(remoteWeather)
    }
}
