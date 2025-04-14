package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.TemperatureSensingSystemEntity
import com.example.weathertracker.shared.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetTempSensingSystemFlowUseCaseImpl(
    val settingsRepository: SettingsRepository,
) : GetTempSensingSystemFlowUseCase {

    override suspend fun invoke(): Flow<TemperatureSensingSystemEntity> {
        return settingsRepository.getTemperatureSensingSystemFlow()
    }
}