package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface GetCurrentWeatherFlowUseCase {
    operator fun invoke(): Flow<WeatherEntity?>
}
