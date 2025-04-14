package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.TemperatureSensingSystemEntity

interface GetTempSensingSystemUseCase {
    suspend operator fun invoke(): TemperatureSensingSystemEntity
}
