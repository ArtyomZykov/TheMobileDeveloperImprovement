package com.example.weathertracker.shared.presentation.weather

import androidx.lifecycle.viewModelScope
import com.example.weathertracker.shared.domain.usecase.GetCurrentWeatherFlowUseCase
import com.example.weathertracker.shared.domain.usecase.SyncCurrentWeatherUseCase
import com.example.weathertracker.shared.presentation.common.StatefulViewModel
import com.example.weathertracker.shared.presentation.common.UiState
import com.example.weathertracker.shared.presentation.weather.CurrentWeatherViewModel.Action
import com.example.weathertracker.shared.presentation.weather.CurrentWeatherViewModel.Event
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlin.onFailure

class CurrentWeatherViewModel(
    private val getCurrentWeatherFlowUseCase: GetCurrentWeatherFlowUseCase,
    private val syncCurrentWeatherUseCase: SyncCurrentWeatherUseCase,
) : StatefulViewModel<UiState<CurrentWeatherState>, Action, Event>(UiState.Loading) {

    override fun onStateObserved() {
        observeWeather()
        syncWeather()
    }

    override fun handleAction(action: Action) {
        when (action) {
            Action.OnPullToRefreshTriggered -> refreshWeather()
            Action.OnForecastButtonClicked -> sendEvent(Event.OpenForecastScreen)
            Action.OnSettingsClicked -> sendEvent(Event.OpenSettingsScreen)
        }
    }

    private fun observeWeather() = viewModelScope.launch {
        getCurrentWeatherFlowUseCase()
            .catch {
                mutableState.value = UiState.Error("Get cashed current weather error")
            }
            .collect { weather ->
                if (weather == null) {
                    mutableState.value = UiState.Error("Error sync current weather")
                } else {
                    val stateModel = weather.toState()
                    mutableState.value = UiState.Success(data = stateModel)
                }
            }
    }

    private fun syncWeather() = viewModelScope.launch {
        runCatching {
            syncCurrentWeatherUseCase()
        }.onFailure {
            sendEvent(Event.ShowSyncErrorView)
        }
    }

    fun refreshWeather() = viewModelScope.launch {
        try {
            syncCurrentWeatherUseCase()
        } catch (_: Exception) {
            mutableState.value = UiState.Error("Error refresh current weather")
        } finally {
            sendEvent(Event.HideRefreshView)
        }
    }

    sealed class Action {
        object OnPullToRefreshTriggered : Action()
        object OnForecastButtonClicked : Action()
        object OnSettingsClicked : Action()
    }

    sealed class Event {
        object OpenForecastScreen : Event()
        object OpenSettingsScreen : Event()
        object ShowSyncErrorView : Event()
        object HideRefreshView : Event()
    }
}
