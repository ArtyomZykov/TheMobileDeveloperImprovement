package com.example.weathertracker.presentation.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertracker.domain.model.DailyForecast
import com.example.weathertracker.domain.usecase.GetDailyForecastUseCase
import com.example.weathertracker.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getDailyForecastUseCase: GetDailyForecastUseCase
) : ViewModel() {

    private val _forecastState = MutableStateFlow<UiState<List<DailyForecast>>>(UiState.Loading)
    val forecastState: StateFlow<UiState<List<DailyForecast>>> = _forecastState

    init {
        refreshForecast()
    }

    fun refreshForecast() {
        viewModelScope.launch {
            _forecastState.value = UiState.Loading
            try {
                val forecast = getDailyForecastUseCase()
                _forecastState.value = UiState.Success(forecast)
            } catch (e: Exception) {
                _forecastState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
} 