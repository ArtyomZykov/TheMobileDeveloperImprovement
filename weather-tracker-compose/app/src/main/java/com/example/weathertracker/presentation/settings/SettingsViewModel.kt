package com.example.weathertracker.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertracker.domain.repository.SettingsRepository
import com.example.weathertracker.work.WorkManagerScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val workManagerScheduler: WorkManagerScheduler
) : ViewModel() {

    val updateInterval: StateFlow<Long> = settingsRepository.getUpdateInterval()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            30L,
        )

    fun setUpdateInterval(minutes: Long) {
        viewModelScope.launch {
            settingsRepository.setUpdateInterval(minutes)
            workManagerScheduler.scheduleWeatherUpdates()
        }
    }
}
