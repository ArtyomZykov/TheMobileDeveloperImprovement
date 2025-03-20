package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.model.DailyForecastEntity
import kotlinx.coroutines.flow.Flow

interface GetDailyForecastFlowUseCase {
    operator fun invoke(): Flow<List<DailyForecastEntity>?>
}
