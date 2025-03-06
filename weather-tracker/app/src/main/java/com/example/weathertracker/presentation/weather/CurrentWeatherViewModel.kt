package com.example.weathertracker.presentation.weather

import androidx.lifecycle.viewModelScope
import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.domain.usecase.GetCurrentWeatherFlowUseCase
import com.example.weathertracker.domain.usecase.SyncCurrentWeatherUseCase
import com.example.weathertracker.presentation.common.StatefulViewModel
import com.example.weathertracker.presentation.common.UiState
import com.example.weathertracker.presentation.weather.CurrentWeatherViewModel.Action
import com.example.weathertracker.presentation.weather.CurrentWeatherViewModel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onFailure

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherFlowUseCase: GetCurrentWeatherFlowUseCase,
    private val syncCurrentWeatherUseCase: SyncCurrentWeatherUseCase,
) : StatefulViewModel<UiState<Weather>, Action, Event>(UiState.Loading) {

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
                mutableState.value = UiState.Error("Get cashed current weather")
            }
            .collect { weather ->
                if (weather == null) {
                    mutableState.value = UiState.Error("Error sync current weather")
                } else {
                    mutableState.value = UiState.Success(weather)
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