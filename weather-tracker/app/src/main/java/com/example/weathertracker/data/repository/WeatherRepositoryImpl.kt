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
import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.domain.repository.WeatherRepository
import com.example.weathertracker.util.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.weathertracker.data.db.entity.WeatherEntity
import com.example.weathertracker.data.db.entity.ForecastEntity
import com.example.weathertracker.domain.model.Location
import java.util.Date

class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val weatherDao: WeatherDao,
    private val forecastDao: ForecastDao,
    private val networkUtils: NetworkUtils
) : WeatherRepository {

    override suspend fun getRemoteCurrentWeather(): Weather {
//        if (!networkUtils.isNetworkAvailable()) {
//            throw IOException("No network connection")
//        }
        val response = api.getCurrentWeather(
            latitude = MOSCOW_LOCATION.latitude,
            longitude = MOSCOW_LOCATION.longitude,
        )
        val weatherEntity = response.toWeatherEntity()
        weatherDao.insertWeather(weatherEntity)
        return weatherEntity.toWeather()
    }

    override suspend fun getRemoteDailyForecast(): List<DailyForecast> {
        val response = api.getDailyForecast(
            latitude = MOSCOW_LOCATION.latitude,
            longitude = MOSCOW_LOCATION.longitude,
        )
        
        val dailyForecasts: List<ForecastEntity> = response
            .list
            .map { it.toForecastEntity() }
        
        forecastDao.insertForecasts(dailyForecasts)
        return dailyForecasts.map { it.toDailyForecast() }
    }

    override fun getCashedWeather(): Flow<Weather?> {
        return weatherDao.getLastWeather().map { it?.toWeather() }
    }

    override fun getCashedDailyForecast(): Flow<List<DailyForecast>> {
        return forecastDao.getAllForecasts().map { entities ->
            entities.map { it.toDailyForecast() }
        }
    }

    override suspend fun cacheWeather(weather: Weather) {
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

    override suspend fun cacheDailyForecast(forecast: List<DailyForecast>) {
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

    private companion object {
        private val MOSCOW_LOCATION = Location(latitude = 55.7558, longitude = 37.6173)
    }
} 