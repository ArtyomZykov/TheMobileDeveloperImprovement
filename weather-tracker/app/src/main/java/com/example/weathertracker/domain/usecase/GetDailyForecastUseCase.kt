package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.model.DailyForecast
import com.example.weathertracker.domain.repository.LocationRepository
import com.example.weathertracker.domain.repository.WeatherRepository
import javax.inject.Inject

class GetDailyForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): List<DailyForecast> {
        val location = locationRepository.getCurrentLocation()
        return weatherRepository.getDailyForecast(location)
    }
} 