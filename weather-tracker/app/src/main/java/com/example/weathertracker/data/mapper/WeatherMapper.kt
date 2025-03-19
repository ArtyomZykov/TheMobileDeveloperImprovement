package com.example.weathertracker.data.mapper

import com.example.weathertracker.data.api.model.CurrentWeatherResponse
import com.example.weathertracker.data.api.model.ForecastResponse
import com.example.weathertracker.data.db.entity.ForecastDbEntity
import com.example.weathertracker.data.db.entity.WeatherDbEntity
import com.example.weathertracker.domain.model.DailyForecastEntity
import com.example.weathertracker.domain.model.WeatherEntity

fun CurrentWeatherResponse.toWeatherDbEntity() = WeatherDbEntity(
    id = id,
    temperature = mainInfo.temp,
    feelsLike = mainInfo.feelsLike,
    humidity = mainInfo.humidity,
    pressure = mainInfo.pressure,
    windSpeed = wind.speed,
    description = weather.firstOrNull()?.description ?: "",
    icon = weather.firstOrNull()?.icon ?: "",
    timestamp = dt
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

fun ForecastResponse.ForecastItem.toForecastDbEntity(): ForecastDbEntity {
    return ForecastDbEntity(
        date = dt,
        minTemp = main.tempMin,
        maxTemp = main.tempMax,
        humidity = main.humidity,
        pressure = main.pressure,
        windSpeed = wind.speed,
        description = weather.firstOrNull()?.description ?: "",
        icon = weather.firstOrNull()?.icon ?: ""
    )
}

fun ForecastDbEntity.toDailyForecastEntity() = DailyForecastEntity(
    date = date,
    minTemp = minTemp,
    maxTemp = maxTemp,
    humidity = humidity,
    pressure = pressure,
    windSpeed = windSpeed,
    description = description,
    icon = icon
) 