package com.example.weathertracker.domain.usecase

interface SyncCurrentWeatherUseCase {
    suspend operator fun invoke()
}
