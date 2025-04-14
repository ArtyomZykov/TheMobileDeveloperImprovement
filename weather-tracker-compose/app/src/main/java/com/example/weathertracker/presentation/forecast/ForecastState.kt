package com.example.weathertracker.presentation.forecast

import androidx.compose.runtime.Immutable
import com.example.weathertracker.domain.model.DailyForecastEntity
import com.example.weathertracker.util.Timestamp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

data class ForecastState(
    val forecasts: ImmutableList<ForecastItemState>,
) {
    @Immutable
    data class ForecastItemState(
        val date: String,
        val maxTemp: Double,
        val minTemp: Double,
        val description: String,
    )
}

internal fun List<DailyForecastEntity>.toState() = ForecastState(
    forecasts = map { forecast ->
        ForecastState.ForecastItemState(
            date = Timestamp.convertUnixTimestampToDate(forecast.date),
            maxTemp = forecast.maxTemp,
            minTemp = forecast.minTemp,
            description = forecast.description,
        )

    }.toPersistentList()
)
