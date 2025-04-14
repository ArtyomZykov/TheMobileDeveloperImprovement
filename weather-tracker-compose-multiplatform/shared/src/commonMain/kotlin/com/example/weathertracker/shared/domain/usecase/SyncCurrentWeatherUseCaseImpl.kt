package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.repository.WeatherRepository

/**
 * Use case for synchronizing the current weather.
 * @property weatherRepository The repository to fetch and cache weather data.
 */
class SyncCurrentWeatherUseCaseImpl(
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
