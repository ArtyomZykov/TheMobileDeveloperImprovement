package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.repository.WeatherRepository
import javax.inject.Inject

class SyncCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke() {
        val remoteWeather = weatherRepository.getRemoteCurrentWeather()
        weatherRepository.cacheWeather(remoteWeather)
    }
}
