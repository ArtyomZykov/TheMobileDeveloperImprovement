package com.example.weathertracker.shared.presentation.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weathertracker.shared.presentation.common.Screen
import com.example.weathertracker.shared.presentation.common.UiState
import com.example.weathertracker.shared.resources.Res
import com.example.weathertracker.shared.resources.current_weather_moscow
import com.example.weathertracker.shared.resources.temperature_format_celsius
import com.example.weathertracker.shared.resources.temperature_format_fahrenheit
import com.example.weathertracker.shared.resources.to_forecast_button
import com.example.weathertracker.shared.util.Temperature.kelvinToCelsius
import com.example.weathertracker.shared.util.Temperature.kelvinToFahrenheit
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

object CurrentWeatherScreen : Screen {

    override val key: String = "/current_weather"

    @Composable
    fun Content(
        onForecastClick: () -> Unit,
        onSettingsClick: () -> Unit,
    ) {
        CurrentWeatherScreen(
            onForecastClick = onForecastClick,
            onSettingsClick = onSettingsClick,
        )
    }
}

@Composable
private fun CurrentWeatherScreen(
    onForecastClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: CurrentWeatherViewModel = koinViewModel()
) {

    val uiState: UiState<CurrentWeatherState> by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingSettingsButton(onClick = onSettingsClick)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(Res.string.current_weather_moscow),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(
                            if (currentState.data.isCelsius) {
                                Res.string.temperature_format_celsius
                            } else {
                                Res.string.temperature_format_fahrenheit
                            },
                            if (currentState.data.isCelsius) {
                                 currentState.data.temperature.kelvinToCelsius()
                            } else {
                                currentState.data.temperature.kelvinToFahrenheit()
                            }
                        ),
                        style = MaterialTheme.typography.displayLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentState.data.description,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(onClick = onForecastClick) {
                        Text(
                            text = stringResource(Res.string.to_forecast_button),
                        )
                    }
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun FloatingSettingsButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
        )
    }
}
