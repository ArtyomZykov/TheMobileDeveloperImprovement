package com.example.weathertracker.shared.presentation.forecast

import androidx.compose.runtime.Immutable
import com.example.weathertracker.shared.domain.model.DailyForecastEntity
import com.example.weathertracker.shared.util.Timestamp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Immutable
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
