package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.WeatherEntity
import com.example.weathertracker.shared.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting the current weather as a flow.
 * @property weatherRepository The repository to fetch weather data from.
 */
class GetCurrentWeatherFlowUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : GetCurrentWeatherFlowUseCase {

    /**
     * Invokes the use case to get the last known weather as a flow.
     * @return A flow emitting the last known weather.
     */
    override operator fun invoke(): Flow<WeatherEntity?> = weatherRepository.getCashedWeather()
}
