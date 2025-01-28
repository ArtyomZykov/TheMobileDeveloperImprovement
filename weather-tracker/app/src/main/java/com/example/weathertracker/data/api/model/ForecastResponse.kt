package com.example.weathertracker.data.api.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: City
) {
    data class ForecastItem(
        val dt: Long,
        val main: MainInfo,
        val weather: List<Weather>,
        val wind: Wind,
        @SerializedName("dt_txt")
        val dateText: String
    )

    data class MainInfo(
        val temp: Double,
        @SerializedName("temp_min")
        val tempMin: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        val humidity: Int,
        val pressure: Int
    )

    data class Weather(
        val description: String,
        val icon: String
    )

    data class Wind(
        val speed: Double
    )

    data class City(
        val name: String,
        val country: String
    )
}