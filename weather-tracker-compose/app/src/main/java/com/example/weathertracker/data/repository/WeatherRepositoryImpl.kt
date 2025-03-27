package com.example.weathertracker.data.repository

import com.example.weathertracker.data.api.OpenWeatherApi
import com.example.weathertracker.data.db.dao.ForecastDao
import com.example.weathertracker.data.db.dao.WeatherDao
import com.example.weathertracker.data.mapper.toDailyForecastEntity
import com.example.weathertracker.data.mapper.toForecastDbEntity
import com.example.weathertracker.data.mapper.toWeatherEntity
import com.example.weathertracker.data.mapper.toWeatherDbEntity
import com.example.weathertracker.domain.model.DailyForecastEntity
import com.example.weathertracker.domain.model.WeatherEntity
import com.example.weathertracker.domain.repository.WeatherRepository
import com.example.weathertracker.util.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.weathertracker.data.db.entity.ForecastDbEntity
import com.example.weathertracker.domain.model.LocationEntity

class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val weatherDao: WeatherDao,
    private val forecastDao: ForecastDao,
    private val networkUtils: NetworkUtils
) : WeatherRepository {

    override suspend fun getRemoteCurrentWeather(): WeatherEntity {
        val response = api.getCurrentWeather(
            latitude = MOSCOW_LOCATION.latitude,
            longitude = MOSCOW_LOCATION.longitude,
        )
        val weatherEntity = response.toWeatherDbEntity()
        weatherDao.insertWeather(weatherEntity)
        return weatherEntity.toWeatherEntity()
    }

    override suspend fun getRemoteDailyForecast(): List<DailyForecastEntity> {
        val response = api.getDailyForecast(
            latitude = MOSCOW_LOCATION.latitude,
            longitude = MOSCOW_LOCATION.longitude,
        )
        
        val dailyForecasts: List<ForecastDbEntity> = response.toForecastDbEntity()
        
        forecastDao.insertForecasts(dailyForecasts)
        return dailyForecasts.map { it.toDailyForecastEntity() }
    }

    override fun getCashedWeather(): Flow<WeatherEntity?> {
        return weatherDao.getLastWeather().map { it?.toWeatherEntity() }
    }

    override fun getCashedDailyForecast(): Flow<List<DailyForecastEntity>?> {
        return forecastDao.getAllForecasts().map { entities ->
            entities.map { it.toDailyForecastEntity() }
        }
    }

    override suspend fun cacheWeather(weather: WeatherEntity) {
        weatherDao.insertWeather(
            com.example.weathertracker.data.db.entity.WeatherDbEntity(
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

    override suspend fun cacheDailyForecast(forecast: List<DailyForecastEntity>) {
        forecastDao.insertForecasts(
            forecast.map {
                ForecastDbEntity(
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
        private val MOSCOW_LOCATION = LocationEntity(latitude = 55.7558, longitude = 37.6173)
    }
} 