package com.example.weathertracker.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertracker.domain.model.Weather
import com.example.weathertracker.domain.usecase.GetCurrentWeatherUseCase
import com.example.weathertracker.domain.usecase.ObserveWeatherUpdatesUseCase
import com.example.weathertracker.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val observeWeatherUpdatesUseCase: ObserveWeatherUpdatesUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<UiState<Weather>>(UiState.Loading)
    val weatherState: StateFlow<UiState<Weather>> = _weatherState

    init {
        observeWeatherUpdates()
        refreshWeather()
    }

    private fun observeWeatherUpdates() {
        viewModelScope.launch {
            observeWeatherUpdatesUseCase()
                .catch { _weatherState.value = UiState.Error(it.message ?: "Unknown error") }
                .collect { weather ->
                    weather?.let {
                        _weatherState.value = UiState.Success(it)
                    }
                }
        }
    }

    fun refreshWeather() {
        viewModelScope.launch {
            _weatherState.value = UiState.Loading
            try {
                val weather = getCurrentWeatherUseCase()
                _weatherState.value = UiState.Success(weather)
            } catch (e: Exception) {
                _weatherState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
} 