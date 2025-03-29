package com.example.weathertracker.domain.usecase

interface SyncDailyForecastUseCase {
    suspend operator fun invoke()
}
