package com.example.weathertracker.presentation.forecast

import androidx.lifecycle.viewModelScope
import com.example.weathertracker.domain.model.DailyForecastEntity
import com.example.weathertracker.domain.usecase.GetDailyForecastFlowUseCase
import com.example.weathertracker.domain.usecase.SyncDailyForecastUseCase
import com.example.weathertracker.presentation.common.StatefulViewModel
import com.example.weathertracker.presentation.common.UiState
import com.example.weathertracker.presentation.forecast.ForecastViewModel.Action
import com.example.weathertracker.presentation.forecast.ForecastViewModel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getDailyForecastFlowUseCase: GetDailyForecastFlowUseCase,
    private val syncDailyForecastUseCase: SyncDailyForecastUseCase,
) : StatefulViewModel<UiState<List<DailyForecastEntity>>, Action, Event>(UiState.Loading) {

    override fun onStateObserved() {
        observeForecast()
        syncForecast()
    }

    override fun handleAction(action: Action) {
        when (action) {
            Action.OnPullToRefreshTriggered -> refreshForecast()
        }
    }

    private fun observeForecast() = viewModelScope.launch {
        getDailyForecastFlowUseCase()
            .catch {
                mutableState.value = UiState.Error("Get cashed forecast error")
            }
            .collect { forecast ->
                if (forecast == null) {
                    mutableState.value = UiState.Error("Error sync forecast")
                } else {
                    mutableState.value = UiState.Success(forecast)
                }
            }
    }

    private fun syncForecast() = viewModelScope.launch {
        runCatching {
            syncDailyForecastUseCase()
        }.onFailure {
            sendEvent(Event.ShowSyncErrorView)
        }
    }

    fun refreshForecast() = viewModelScope.launch {
        try {
            syncDailyForecastUseCase()
        } catch (_: Exception) {
            mutableState.value = UiState.Error("Error refresh forecast")
        } finally {
            sendEvent(Event.HideRefreshView)
        }
    }

    sealed class Action {
        object OnPullToRefreshTriggered : Action()
    }

    sealed class Event {
        object ShowSyncErrorView : Event()
        object HideRefreshView : Event()
    }
} 