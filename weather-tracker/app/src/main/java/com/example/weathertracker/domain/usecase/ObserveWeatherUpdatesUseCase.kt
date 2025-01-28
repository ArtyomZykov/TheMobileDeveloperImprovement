package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveWeatherUpdatesUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<Weather?> = weatherRepository.getLastKnownWeather()
} 