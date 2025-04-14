package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.TemperatureSensingSystemEntity
import com.example.weathertracker.shared.domain.repository.SettingsRepository

class SetTempSensingSystemUseCaseImpl(
    private val settingsRepository: SettingsRepository,
) : SetTempSensingSystemUseCase {

    override suspend fun invoke(system: TemperatureSensingSystemEntity) {
        settingsRepository.setTemperatureSensingSystem(system)
    }
}
