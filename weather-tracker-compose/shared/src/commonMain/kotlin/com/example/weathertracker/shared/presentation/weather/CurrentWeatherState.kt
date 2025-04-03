package com.example.weathertracker.shared.presentation.weather

import androidx.compose.runtime.Immutable
import com.example.weathertracker.shared.domain.model.WeatherEntity

@Immutable
data class CurrentWeatherState(
    val temperature: Double,
    val description: String,
)

internal fun WeatherEntity.toState() = CurrentWeatherState(
    temperature = temperature,
    description = description,
)
