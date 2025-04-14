package com.example.weathertracker.domain.usecase

import com.example.weathertracker.domain.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface GetCurrentWeatherFlowUseCase {
    operator fun invoke(): Flow<WeatherEntity?>
}
