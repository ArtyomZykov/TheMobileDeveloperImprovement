package com.example.weathertracker.shared.data.mapper

import com.example.weathertracker.shared.data.api.model.CurrentWeatherResponse
import com.example.weathertracker.shared.data.db.entity.WeatherDbEntity
import com.example.weathertracker.shared.domain.model.WeatherEntity

fun CurrentWeatherResponse.toWeatherDbEntity() = WeatherDbEntity(
    id = requireNotNull(id),
    kelvinTemperature = requireNotNull(mainInfo?.kelvinTemp),
    feelsLike = requireNotNull(mainInfo?.feelsLike),
    humidity = requireNotNull(mainInfo?.humidity),
    pressure = requireNotNull(mainInfo?.pressure),
    windSpeed = requireNotNull(wind?.speed),
    description = weather?.firstOrNull()?.description ?: "",
    icon = weather?.firstOrNull()?.icon ?: "",
    timestamp = requireNotNull(dt),
)

fun WeatherDbEntity.toWeatherEntity() = WeatherEntity(
    id = id,
    temperature = kelvinTemperature,
    feelsLike = feelsLike,
    humidity = humidity,
    pressure = pressure,
    windSpeed = windSpeed,
    description = description,
    icon = icon,
    timestamp = timestamp
)
