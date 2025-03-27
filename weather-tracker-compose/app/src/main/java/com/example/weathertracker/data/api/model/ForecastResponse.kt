package com.example.weathertracker.data.api.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list")
    val list: List<ForecastItem>? = null,
    @SerializedName("city")
    val city: City? = null,
) {
    data class ForecastItem(
        @SerializedName("dt")
        val dt: Long? = null,
        @SerializedName("main")
        val main: MainInfo? = null,
        @SerializedName("weather")
        val weather: List<Weather>? = null,
        @SerializedName("wind")
        val wind: Wind? = null,
        @SerializedName("dt_txt")
        val dateText: String? = null,
    )

    data class MainInfo(
        @SerializedName("temp")
        val temp: Double? = null,
        @SerializedName("temp_min")
        val tempMin: Double? = null,
        @SerializedName("temp_max")
        val tempMax: Double? = null,
        @SerializedName("humidity")
        val humidity: Int? = null,
        @SerializedName("pressure")
        val pressure: Int? = null,
    )

    data class Weather(
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("icon")
        val icon: String? = null,
    )

    data class Wind(
        @SerializedName("speed")
        val speed: Double? = null,
    )

    data class City(
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("country")
        val country: String? = null,
    )
}
