package com.example.weathertracker.shared.data.repository

import com.example.weathertracker.shared.data.api.model.CurrentWeatherResponse
import com.example.weathertracker.shared.data.api.model.ForecastResponse
import com.example.weathertracker.shared.data.db.dao.ForecastDao
import com.example.weathertracker.shared.data.db.dao.WeatherDao
import com.example.weathertracker.shared.data.mapper.toDailyForecastEntity
import com.example.weathertracker.shared.data.mapper.toForecastDbEntity
import com.example.weathertracker.shared.data.mapper.toWeatherEntity
import com.example.weathertracker.shared.data.mapper.toWeatherDbEntity
import com.example.weathertracker.shared.domain.model.DailyForecastEntity
import com.example.weathertracker.shared.domain.model.WeatherEntity
import com.example.weathertracker.shared.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.weathertracker.shared.data.db.entity.ForecastDbEntity
import com.example.weathertracker.shared.domain.model.LocationEntity
import com.example.weathertracker.shared.data.db.entity.WeatherDbEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.encodedPath

class WeatherRepositoryImpl(
    private val httpClient: HttpClient,
    private val weatherDao: WeatherDao,
    private val forecastDao: ForecastDao,
) : WeatherRepository {

    override suspend fun getRemoteCurrentWeather(): WeatherEntity {
        val response: CurrentWeatherResponse = httpClient.get {
            url("weather")
            parameter("lat", MOSCOW_LOCATION.latitude)
            parameter("lon", MOSCOW_LOCATION.longitude)
            parameter("units", "metric")
        }.body()

        val weatherEntity = response.toWeatherDbEntity()
        weatherDao.insertWeather(weatherEntity)
        return weatherEntity.toWeatherEntity()
    }

    override suspend fun getRemoteDailyForecast(): List<DailyForecastEntity> {
        val response: ForecastResponse = httpClient.get {
            url("forecast")
            parameter("lat", MOSCOW_LOCATION.latitude)
            parameter("lon", MOSCOW_LOCATION.longitude)
            parameter("units", "metric")
            parameter("cnt", 40)
        }.body()

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
            WeatherDbEntity(
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
        private val MOSCOW_LOCATION = LocationEntity(
            latitude = 55.7558,
            longitude = 37.6173,
        )
    }
}
