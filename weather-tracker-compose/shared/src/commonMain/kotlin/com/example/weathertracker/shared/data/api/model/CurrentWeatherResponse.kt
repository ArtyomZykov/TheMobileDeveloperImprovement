package com.example.weathertracker.shared.data.api.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CurrentWeatherResponse(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("main")
    val mainInfo: MainInfo? = null,
    @SerialName("wind")
    val wind: Wind? = null,
    @SerialName("weather")
    val weather: List<Weather>? = null,
    @SerialName("dt")
    val dt: Long? = null,
) {

    @Serializable
    data class MainInfo(
        @SerialName("temp")
        val temp: Double? = null,
        @SerialName("feels_like")
        val feelsLike: Double? = null,
        @SerialName("humidity")
        val humidity: Int? = null,
        @SerialName("pressure")
        val pressure: Int? = null,
    )

    @Serializable
    data class Wind(
        @SerialName("speed")
        val speed: Double? = null,
    )

    @Serializable
    data class Weather(
        @SerialName("description")
        val description: String? = null,
        @SerialName("icon")
        val icon: String? = null,
    )
}
