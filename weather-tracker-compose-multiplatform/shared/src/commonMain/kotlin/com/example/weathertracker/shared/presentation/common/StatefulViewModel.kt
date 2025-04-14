package com.example.weathertracker.shared.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class StatefulViewModel<State : Any, Action, Event>(initialState: State) : ViewModel() {

    protected val mutableState = MutableStateFlow<State>(initialState)
    internal val state: StateFlow<State> = mutableState
        .onStart { onStateObserved() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            initialState,
        )

    /**
     * Called when the state is first observed.
     * Analog of init() in conjunction with UI lifecycle.
     */
    internal open fun onStateObserved() {}

    private val mutableEvent: Channel<Event> = Channel()
    internal val event: Flow<Event> get() = mutableEvent.receiveAsFlow()

    internal fun sendEvent(event: Event) {
        viewModelScope.launch {
            mutableEvent.send(event)
        }
    }

    internal abstract fun handleAction(action: Action)
}
