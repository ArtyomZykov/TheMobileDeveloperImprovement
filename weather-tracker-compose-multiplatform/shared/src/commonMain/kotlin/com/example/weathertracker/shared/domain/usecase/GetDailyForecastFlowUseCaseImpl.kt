package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.DailyForecastEntity
import com.example.weathertracker.shared.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting the daily forecast as a flow.
 * @property weatherRepository The repository to fetch weather data from.
 */
class GetDailyForecastFlowUseCaseImpl(
    private val weatherRepository: WeatherRepository,
) : GetDailyForecastFlowUseCase {

    /**
     * Invokes the use case to get the last known daily forecast as a flow.
     * @return A flow emitting the last known daily forecast.
     */
    override operator fun invoke(): Flow<List<DailyForecastEntity>?> =
        weatherRepository.getCashedDailyForecast()
}
