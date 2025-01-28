package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.domain.repository.LocationRepository
import com.example.weathertracker.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Weather {
        val location = locationRepository.getCurrentLocation()
        return weatherRepository.getCurrentWeather(location)
    }
} 