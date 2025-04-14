package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.model.DailyForecastEntity
import com.example.weathertracker.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting the daily forecast as a flow.
 * @property weatherRepository The repository to fetch weather data from.
 */
class GetDailyForecastFlowUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : GetDailyForecastFlowUseCase {

    /**
     * Invokes the use case to get the last known daily forecast as a flow.
     * @return A flow emitting the last known daily forecast.
     */
    override operator fun invoke(): Flow<List<DailyForecastEntity>?> =
        weatherRepository.getCashedDailyForecast()
}
