package com.example.weathertracker.data.mapper

import com.example.weathertracker.data.api.model.CurrentWeatherResponse
import com.example.weathertracker.data.api.model.ForecastResponse
import com.example.weathertracker.data.db.entity.ForecastDbEntity
import com.example.weathertracker.data.db.entity.WeatherDbEntity
import com.example.weathertracker.domain.model.DailyForecastEntity
import com.example.weathertracker.domain.model.WeatherEntity

fun CurrentWeatherResponse.toWeatherDbEntity() = WeatherDbEntity(
    id = requireNotNull(id),
    temperature = requireNotNull(mainInfo?.temp),
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
    temperature = temperature,
    feelsLike = feelsLike,
    humidity = humidity,
    pressure = pressure,
    windSpeed = windSpeed,
    description = description,
    icon = icon,
    timestamp = timestamp
)
