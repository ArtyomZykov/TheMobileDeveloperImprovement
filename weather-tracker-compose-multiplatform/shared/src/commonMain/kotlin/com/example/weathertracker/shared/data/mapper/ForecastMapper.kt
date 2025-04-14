package com.example.weathertracker.shared.data.mapper

import com.example.weathertracker.shared.data.api.model.ForecastResponse
import com.example.weathertracker.shared.data.db.entity.ForecastDbEntity
import com.example.weathertracker.shared.domain.model.DailyForecastEntity

fun ForecastResponse.toForecastDbEntity(): List<ForecastDbEntity> {
    return requireNotNull(list)
        .map { item ->
            ForecastDbEntity(
                date = requireNotNull(item.dt),
                minTemp = requireNotNull(item.main?.tempMin),
                maxTemp = requireNotNull(item.main?.tempMax),
                humidity = requireNotNull(item.main?.humidity),
                pressure = requireNotNull(item.main?.pressure),
                windSpeed = requireNotNull(item.wind?.speed),
                description = item.weather?.firstOrNull()?.description ?: "",
                icon = item.weather?.firstOrNull()?.icon ?: ""
            )
        }
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
