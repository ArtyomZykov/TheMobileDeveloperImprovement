package com.example.weathertracker.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class StatefulViewModel<State : Any, Action, Event>(initialState: State): ViewModel() {

    protected val mutableState = MutableStateFlow<State>(initialState)
    internal val state: StateFlow<State> = mutableState

    protected val mutableEvent: Channel<Event> = Channel()
    internal val event: Flow<Event> get() = mutableEvent.receiveAsFlow()

    internal abstract fun handleAction(action: Action)
}
