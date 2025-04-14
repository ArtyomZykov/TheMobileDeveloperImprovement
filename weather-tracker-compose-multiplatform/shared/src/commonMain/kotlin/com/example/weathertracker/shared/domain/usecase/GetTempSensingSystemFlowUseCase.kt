package com.example.weathertracker.shared.domain.usecase

import com.example.weathertracker.shared.domain.model.TemperatureSensingSystemEntity
import kotlinx.coroutines.flow.Flow

interface GetTempSensingSystemFlowUseCase {
    suspend operator fun invoke(): Flow<TemperatureSensingSystemEntity>
}
