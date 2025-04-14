package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.TemperatureSensingSystemEntity
import com.example.weathertracker.shared.domain.repository.SettingsRepository

class GetTempSensingSystemUseCaseImpl(
    private val settingsRepository: SettingsRepository
) : GetTempSensingSystemUseCase {

    override suspend fun invoke(): TemperatureSensingSystemEntity {
        return settingsRepository.getTemperatureSensingSystem()
    }
}