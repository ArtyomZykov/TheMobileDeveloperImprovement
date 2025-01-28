package com.example.weathertracker.data.api.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val id: Long,
    @SerializedName("main")
    val mainInfo: MainInfo,
    val wind: Wind,
    val weather: List<Weather>,
    val dt: Long
) {
    data class MainInfo(
        val temp: Double,
        @SerializedName("feels_like")
        val feelsLike: Double,
        val humidity: Int,
        val pressure: Int
    )

    data class Wind(
        val speed: Double
    )

    data class Weather(
        val description: String,
        val icon: String
    )
} 