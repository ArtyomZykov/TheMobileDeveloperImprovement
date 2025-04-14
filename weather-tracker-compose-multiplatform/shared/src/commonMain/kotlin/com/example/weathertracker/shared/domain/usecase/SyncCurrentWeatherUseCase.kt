package com.example.weathertracker.shared.domain.usecase

interface SyncCurrentWeatherUseCase {
    suspend operator fun invoke()
}
