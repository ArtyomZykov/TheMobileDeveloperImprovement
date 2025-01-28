package com.example.weathertracker.data.mapper

import com.example.weathertracker.data.api.model.CurrentWeatherResponse
import com.example.weathertracker.data.api.model.ForecastResponse
import com.example.weathertracker.data.db.entity.ForecastEntity
import com.example.weathertracker.data.db.entity.WeatherEntity
import com.example.weathertracker.domain.model.DailyForecast
import com.example.weathertracker.domain.model.Weather

fun CurrentWeatherResponse.toWeatherEntity() = WeatherEntity(
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

fun WeatherEntity.toWeather() = Weather(
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

fun ForecastResponse.ForecastItem.toForecastEntity(): ForecastEntity {
    return ForecastEntity(
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

fun ForecastEntity.toDailyForecast() = DailyForecast(
    date = date,
    minTemp = minTemp,
    maxTemp = maxTemp,
    humidity = humidity,
    pressure = pressure,
    windSpeed = windSpeed,
    description = description,
    icon = icon
) 