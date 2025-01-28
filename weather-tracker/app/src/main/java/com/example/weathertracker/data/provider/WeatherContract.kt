package com.example.weathertracker.data.provider

import android.net.Uri

object WeatherContract {
    const val AUTHORITY = "com.example.weathertracker.provider.weather"
    
    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")
    
    object Weather {
        val CONTENT_URI: Uri = Uri.withAppendedPath(WeatherContract.CONTENT_URI, "weather")
        const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.weather"
        const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.weather"
        
        const val TABLE_NAME = "weather"
        const val COLUMN_ID = "id"
        const val COLUMN_TEMPERATURE = "temperature"
        const val COLUMN_FEELS_LIKE = "feelsLike"
        const val COLUMN_HUMIDITY = "humidity"
        const val COLUMN_PRESSURE = "pressure"
        const val COLUMN_WIND_SPEED = "windSpeed"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ICON = "icon"
        const val COLUMN_TIMESTAMP = "timestamp"
    }
    
    object Forecast {
        val CONTENT_URI: Uri = Uri.withAppendedPath(WeatherContract.CONTENT_URI, "forecast")
        const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.forecast"
        const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.forecast"
        
        const val TABLE_NAME = "forecast"
        const val COLUMN_DATE = "date"
        const val COLUMN_MIN_TEMP = "minTemp"
        const val COLUMN_MAX_TEMP = "maxTemp"
        const val COLUMN_HUMIDITY = "humidity"
        const val COLUMN_PRESSURE = "pressure"
        const val COLUMN_WIND_SPEED = "windSpeed"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ICON = "icon"
    }
} 