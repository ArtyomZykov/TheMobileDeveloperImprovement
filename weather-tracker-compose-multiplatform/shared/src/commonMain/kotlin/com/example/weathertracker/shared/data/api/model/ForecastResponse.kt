package com.example.weathertracker.shared.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("list")
    val list: List<ForecastItem>? = null,
    @SerialName("city")
    val city: City? = null,
) {

    @Serializable
    data class ForecastItem(
        @SerialName("dt")
        val dt: Long? = null,
        @SerialName("main")
        val main: MainInfo? = null,
        @SerialName("weather")
        val weather: List<Weather>? = null,
        @SerialName("wind")
        val wind: Wind? = null,
        @SerialName("dt_txt")
        val dateText: String? = null,
    )

    @Serializable
    data class MainInfo(
        @SerialName("temp")
        val temp: Double? = null,
        @SerialName("temp_min")
        val tempMin: Double? = null,
        @SerialName("temp_max")
        val tempMax: Double? = null,
        @SerialName("humidity")
        val humidity: Int? = null,
        @SerialName("pressure")
        val pressure: Int? = null,
    )

    @Serializable
    data class Weather(
        @SerialName("description")
        val description: String? = null,
        @SerialName("icon")
        val icon: String? = null,
    )

    @Serializable
    data class Wind(
        @SerialName("speed")
        val speed: Double? = null,
    )

    @Serializable
    data class City(
        @SerialName("name")
        val name: String? = null,
        @SerialName("country")
        val country: String? = null,
    )
}
