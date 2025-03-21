package com.example.weathertracker.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.weathertracker.data.db.WeatherDatabase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class WeatherContentProvider : ContentProvider() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WeatherContentProviderEntryPoint {
        fun weatherDatabase(): WeatherDatabase
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(WeatherContract.AUTHORITY, "weather", WEATHER)
        addURI(WeatherContract.AUTHORITY, "weather/#", WEATHER_ID)
        addURI(WeatherContract.AUTHORITY, "forecast", FORECAST)
        addURI(WeatherContract.AUTHORITY, "forecast/#", FORECAST_ID)
    }

    private val database: WeatherDatabase by lazy {
        val appContext = context?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            WeatherContentProviderEntryPoint::class.java
        )
        hiltEntryPoint.weatherDatabase()
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val context = context ?: return null

        val cursor = when (uriMatcher.match(uri)) {
            WEATHER -> {
                database.weatherDao().getWeatherCursor()
            }
            WEATHER_ID -> {
                val id = uri.lastPathSegment?.toLongOrNull() ?: return null
                database.weatherDao().getWeatherByIdCursor(id)
            }
            FORECAST -> {
                database.forecastDao().getForecastCursor()
            }
            FORECAST_ID -> {
                val date = uri.lastPathSegment?.toLongOrNull() ?: return null
                database.forecastDao().getForecastByDateCursor(date)
            }
            else -> null
        }

        cursor?.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            WEATHER -> WeatherContract.Weather.CONTENT_TYPE
            WEATHER_ID -> WeatherContract.Weather.CONTENT_ITEM_TYPE
            FORECAST -> WeatherContract.Forecast.CONTENT_TYPE
            FORECAST_ID -> WeatherContract.Forecast.CONTENT_ITEM_TYPE
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null // Только для чтения

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0 // Только для чтения

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0 // Только для чтения

    companion object {
        private const val WEATHER = 1
        private const val WEATHER_ID = 2
        private const val FORECAST = 3
        private const val FORECAST_ID = 4
    }
} 