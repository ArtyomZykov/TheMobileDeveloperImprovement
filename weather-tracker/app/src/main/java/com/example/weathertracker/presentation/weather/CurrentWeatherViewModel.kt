package com.example.weathertracker.presentation.weather

import androidx.lifecycle.viewModelScope
import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.domain.usecase.GetCurrentWeatherUseCase
import com.example.weathertracker.domain.usecase.ObserveWeatherUpdatesUseCase
import com.example.weathertracker.presentation.common.StatefulViewModel
import com.example.weathertracker.presentation.common.UiState
import com.example.weathertracker.presentation.weather.CurrentWeatherViewModel.Action
import com.example.weathertracker.presentation.weather.CurrentWeatherViewModel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val observeWeatherUpdatesUseCase: ObserveWeatherUpdatesUseCase
) : StatefulViewModel<UiState<Weather>, Action, Event>(UiState.Loading) {

    init {
        observeWeatherUpdates()
        refreshWeather()
    }

    override fun handleAction(action: Action) = when(action) {
        Action.RefreshWeather -> refreshWeather()
    }

    private fun observeWeatherUpdates() {
        viewModelScope.launch {
            mutableEvent.send(Event.RefreshWeather)
            observeWeatherUpdatesUseCase()
                .catch { mutableState.value = UiState.Error(it.message ?: "Unknown error") }
                .collect { weather ->
                    weather?.let {
                        mutableState.value = UiState.Success(it)
                    }
                }
        }
    }

    fun refreshWeather() {
        viewModelScope.launch {
            mutableState.value = UiState.Loading
            try {
                val weather = getCurrentWeatherUseCase()
                mutableState.value = UiState.Success(weather)
            } catch (e: Exception) {
                mutableState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed class Action {
        object RefreshWeather : Action()
    }

    sealed class Event {
        object RefreshWeather : Event()
    }
} 