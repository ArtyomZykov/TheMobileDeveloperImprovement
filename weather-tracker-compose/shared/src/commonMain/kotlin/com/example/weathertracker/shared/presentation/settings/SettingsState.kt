package com.example.weathertracker.shared.presentation.settings

import androidx.compose.runtime.Immutable
import com.example.weathertracker.shared.domain.model.TemperatureSensingSystemEntity

@Immutable
data class SettingsState(
    val temperatureSensingSystem: TemperatureSensingSystemState,
)

@Immutable
sealed class TemperatureSensingSystemState {
    object Celsius : TemperatureSensingSystemState()
    object Fahrenheit : TemperatureSensingSystemState()
}

internal fun TemperatureSensingSystemEntity.toState() = when (this) {
    TemperatureSensingSystemEntity.Celsius -> TemperatureSensingSystemState.Celsius
    TemperatureSensingSystemEntity.Fahrenheit -> TemperatureSensingSystemState.Fahrenheit
}

internal fun TemperatureSensingSystemState.toEntity() = when (this) {
    TemperatureSensingSystemState.Celsius -> TemperatureSensingSystemEntity.Celsius
    TemperatureSensingSystemState.Fahrenheit -> TemperatureSensingSystemEntity.Fahrenheit
}
