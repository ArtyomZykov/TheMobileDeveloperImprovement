package com.example.weathertracker.shared.presentation.settings

import androidx.lifecycle.viewModelScope
import com.example.weathertracker.shared.domain.usecase.GetTempSensingSystemFlowUseCase
import com.example.weathertracker.shared.domain.usecase.SetTempSensingSystemUseCase
import com.example.weathertracker.shared.presentation.common.StatefulViewModel
import com.example.weathertracker.shared.presentation.common.UiState
import com.example.weathertracker.shared.presentation.settings.SettingsViewModel.Action
import kotlinx.coroutines.launch

class SettingsViewModel(
    val getTempSensingSystemFlowUseCase: GetTempSensingSystemFlowUseCase,
    val setTempSensingSystemUseCase: SetTempSensingSystemUseCase,
) : StatefulViewModel<UiState<SettingsState>, Action, Unit>(UiState.Loading) {

    override fun onStateObserved() {
        viewModelScope.launch {
            getTempSensingSystemFlowUseCase()
                .collect { tempSensingSystem ->
                    mutableState.value = UiState.Success(
                        SettingsState(temperatureSensingSystem = tempSensingSystem.toState())
                    )
                }
        }
    }

    override fun handleAction(action: Action) {
        when (action) {
            is Action.OnChangeTemperatureSystem -> {
                viewModelScope.launch {
                    setTempSensingSystemUseCase.invoke(action.tempSystem.toEntity())
                }
            }
        }
    }

    sealed class Action {
        data class OnChangeTemperatureSystem(val tempSystem: TemperatureSensingSystemState) :
            Action()
    }
}