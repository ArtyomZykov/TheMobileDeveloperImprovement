package com.example.weathertracker.shared.domain.model

sealed interface TemperatureSensingSystemEntity {

    data object Celsius : TemperatureSensingSystemEntity
    data object Fahrenheit : TemperatureSensingSystemEntity

    fun toKey(): String = when (this) {
        is Celsius -> "Celsius"
        is Fahrenheit -> "Fahrenheit"
    }

    companion object {
        fun fromKey(key: String): TemperatureSensingSystemEntity = when (key) {
            "Celsius" -> Celsius
            "Fahrenheit" -> Fahrenheit
            else -> throw IllegalArgumentException("Unknown temperature sensing system key: $key")
        }
    }
}
