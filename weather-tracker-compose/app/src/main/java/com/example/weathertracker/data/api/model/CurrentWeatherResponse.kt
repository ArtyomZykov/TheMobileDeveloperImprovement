package com.example.weathertracker.data.api.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("main")
    val mainInfo: MainInfo? = null,
    @SerializedName("wind")
    val wind: Wind? = null,
    @SerializedName("weather")
    val weather: List<Weather>? = null,
    @SerializedName("dt")
    val dt: Long? = null,
) {
    data class MainInfo(
        @SerializedName("temp")
        val temp: Double? = null,
        @SerializedName("feels_like")
        val feelsLike: Double? = null,
        @SerializedName("humidity")
        val humidity: Int? = null,
        @SerializedName("pressure")
        val pressure: Int? = null,
    )

    data class Wind(
        @SerializedName("speed")
        val speed: Double? = null,
    )

    data class Weather(
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("icon")
        val icon: String? = null,
    )
}
