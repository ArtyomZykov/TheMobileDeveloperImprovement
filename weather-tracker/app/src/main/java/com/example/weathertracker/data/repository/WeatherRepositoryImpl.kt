package com.example.weathertracker.data.repository

import com.example.weathertracker.BuildConfig
import com.example.weathertracker.data.api.OpenWeatherApi
import com.example.weathertracker.data.db.dao.ForecastDao
import com.example.weathertracker.data.db.dao.WeatherDao
import com.example.weathertracker.data.mapper.toDailyForecast
import com.example.weathertracker.data.mapper.toForecastEntity
import com.example.weathertracker.data.mapper.toWeather
import com.example.weathertracker.data.mapper.toWeatherEntity
import com.example.weathertracker.domain.model.DailyForecast
import com.example.weathertracker.domain.model.Location
import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.domain.repository.WeatherRepository
import com.example.weathertracker.util.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import java.io.IOException
import com.example.weathertracker.data.db.entity.WeatherEntity
import com.example.weathertracker.data.db.entity.ForecastEntity

class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val weatherDao: WeatherDao,
    private val forecastDao: ForecastDao,
    private val networkUtils: NetworkUtils
) : WeatherRepository {

    override suspend fun getCurrentWeather(location: Location): Weather {
        if (!networkUtils.isNetworkAvailable()) {
            throw IOException("No network connection")
        }
        val response = api.getCurrentWeather(
            latitude = location.latitude,
            longitude = location.longitude,
            apiKey = BuildConfig.WEATHER_API_KEY
        )
        val weatherEntity = response.toWeatherEntity()
        weatherDao.insertWeather(weatherEntity)
        return weatherEntity.toWeather()
    }

    override suspend fun getDailyForecast(location: Location): List<DailyForecast> {
        if (!networkUtils.isNetworkAvailable()) {
            throw IOException("No network connection")
        }
        val response = api.getDailyForecast(
            latitude = location.latitude,
            longitude = location.longitude,
            apiKey = BuildConfig.WEATHER_API_KEY
        )
        
        // Группируем прогнозы по дням и берем первый прогноз для каждого дня
        val dailyForecasts = response.list
            .groupBy { it.dateText.substring(0, 10) } // Группируем по дате (YYYY-MM-DD)
            .map { it.value.first() } // Берем первый прогноз для каждого дня
            .map { it.toForecastEntity() }
        
        forecastDao.insertForecasts(dailyForecasts)
        return dailyForecasts.map { it.toDailyForecast() }
    }

    override fun getLastKnownWeather(): Flow<Weather?> {
        return weatherDao.getLastWeather().map { it?.toWeather() }
    }

    override fun getDailyForecastFromCache(): Flow<List<DailyForecast>> {
        return forecastDao.getAllForecasts().map { entities ->
            entities.map { it.toDailyForecast() }
        }
    }

    override suspend fun updateWeatherCache(weather: Weather) {
        weatherDao.insertWeather(
            WeatherEntity(
                id = weather.id,
                temperature = weather.temperature,
                feelsLike = weather.feelsLike,
                humidity = weather.humidity,
                pressure = weather.pressure,
                windSpeed = weather.windSpeed,
                description = weather.description,
                icon = weather.icon,
                timestamp = weather.timestamp
            )
        )
    }

    override suspend fun updateForecastCache(forecast: List<DailyForecast>) {
        forecastDao.insertForecasts(
            forecast.map {
                ForecastEntity(
                    date = it.date,
                    minTemp = it.minTemp,
                    maxTemp = it.maxTemp,
                    humidity = it.humidity,
                    pressure = it.pressure,
                    windSpeed = it.windSpeed,
                    description = it.description,
                    icon = it.icon
                )
            }
        )
    }
} 