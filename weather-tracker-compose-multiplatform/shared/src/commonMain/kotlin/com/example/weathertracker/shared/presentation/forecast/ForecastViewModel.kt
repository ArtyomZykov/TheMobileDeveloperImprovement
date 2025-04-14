package com.example.weathertracker.shared.presentation.forecast

import androidx.lifecycle.viewModelScope
import com.example.weathertracker.shared.domain.usecase.GetDailyForecastFlowUseCase
import com.example.weathertracker.shared.domain.usecase.SyncDailyForecastUseCase
import com.example.weathertracker.shared.presentation.common.StatefulViewModel
import com.example.weathertracker.shared.presentation.common.UiState
import com.example.weathertracker.shared.presentation.forecast.ForecastViewModel.Action
import com.example.weathertracker.shared.presentation.forecast.ForecastViewModel.Event
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ForecastViewModel(
    private val getDailyForecastFlowUseCase: GetDailyForecastFlowUseCase,
    private val syncDailyForecastUseCase: SyncDailyForecastUseCase,
) : StatefulViewModel<UiState<ForecastState>, Action, Event>(UiState.Loading) {

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
                if (forecast != null) {
                    val stateModel = forecast.toState()
                    mutableState.value = UiState.Success(stateModel)
                } else {
                    mutableState.value = UiState.Error("Error sync forecast")
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
