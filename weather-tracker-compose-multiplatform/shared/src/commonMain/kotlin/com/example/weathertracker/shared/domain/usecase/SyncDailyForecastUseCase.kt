package com.example.weathertracker.shared.domain.usecase

interface SyncDailyForecastUseCase {
    suspend operator fun invoke()
}
