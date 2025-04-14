package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.TemperatureSensingSystemEntity

interface SetTempSensingSystemUseCase {
    suspend operator fun invoke(system: TemperatureSensingSystemEntity)
}
