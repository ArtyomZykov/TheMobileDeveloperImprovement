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

    val state: StateFlow<UiState<Weather>>
        field = MutableStateFlow<UiState<Weather>>(UiState.Loading)

    init {
        observeWeatherUpdates()
        refreshWeather()
    }

    private fun observeWeatherUpdates() {
        viewModelScope.launch {
            observeWeatherUpdatesUseCase()
                .catch { state.value = UiState.Error(it.message ?: "Unknown error") }
                .collect { weather ->
                    weather?.let {
                        state.value = UiState.Success(it)
                    }
                }
        }
    }

    fun refreshWeather() {
        viewModelScope.launch {
            state.value = UiState.Loading
            try {
                val weather = getCurrentWeatherUseCase()
                state.value = UiState.Success(weather)
            } catch (e: Exception) {
                state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
} 