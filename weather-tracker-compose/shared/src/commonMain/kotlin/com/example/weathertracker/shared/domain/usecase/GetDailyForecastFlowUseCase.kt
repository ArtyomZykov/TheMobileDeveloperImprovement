package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.DailyForecastEntity
import kotlinx.coroutines.flow.Flow

interface GetDailyForecastFlowUseCase {
    operator fun invoke(): Flow<List<DailyForecastEntity>?>
}
