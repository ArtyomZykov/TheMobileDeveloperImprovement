package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.model.DailyForecast
import com.example.weathertracker.domain.repository.WeatherRepository
import javax.inject.Inject

class GetDailyForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(): List<DailyForecast> {
        return weatherRepository.getRemoteDailyForecast()
    }
} 