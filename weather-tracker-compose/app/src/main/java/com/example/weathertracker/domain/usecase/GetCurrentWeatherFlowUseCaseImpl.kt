package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.model.WeatherEntity
import com.example.weathertracker.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting the current weather as a flow.
 * @property weatherRepository The repository to fetch weather data from.
 */
class GetCurrentWeatherFlowUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : GetCurrentWeatherFlowUseCase {

    /**
     * Invokes the use case to get the last known weather as a flow.
     * @return A flow emitting the last known weather.
     */
    override operator fun invoke(): Flow<WeatherEntity?> = weatherRepository.getCashedWeather()
}
