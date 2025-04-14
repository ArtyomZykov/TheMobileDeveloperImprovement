package com.example.weathertracker.shared.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weathertracker.shared.presentation.common.Screen
import com.example.weathertracker.shared.presentation.common.UiState
import com.example.weathertracker.shared.presentation.settings.SettingsViewModel.Action
import org.koin.compose.viewmodel.koinViewModel

object SettingsScreen : Screen {

    override val key: String = "/settings"

    @Composable
    fun Content(onBackClick: () -> Unit) {
        SettingsScreen(onBackClick = onBackClick)
    }
}

@Composable
private fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {

    val uiState: UiState<SettingsState> by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val currentState = uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                currentState.data

                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = currentState.data.temperatureSensingSystem is TemperatureSensingSystemState.Celsius,
                            onClick = {
                                viewModel.handleAction(
                                    Action.OnChangeTemperatureSystem(
                                        TemperatureSensingSystemState.Celsius
                                    )
                                )
                            }
                        )
                        Text("Celsius")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = currentState.data.temperatureSensingSystem is TemperatureSensingSystemState.Fahrenheit,
                            onClick = {
                                viewModel.handleAction(
                                    Action.OnChangeTemperatureSystem(
                                        TemperatureSensingSystemState.Fahrenheit
                                    )
                                )
                            }
                        )
                        Text("Fahrenheit")
                    }
                }
            }

            is UiState.Error -> Unit
        }
    }
}
