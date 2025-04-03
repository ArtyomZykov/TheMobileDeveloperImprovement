package com.example.weathertracker.shared.presentation.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weathertracker.shared.presentation.common.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CurrentWeatherScreen(
    onForecastClick: () -> Unit,
    viewModel: CurrentWeatherViewModel = koinViewModel()
) {

    val uiState: UiState<CurrentWeatherState> by viewModel.state.collectAsState()

    Scaffold { paddingValues ->
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
                (uiState as UiState.Success<CurrentWeatherState>).data.temperature
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "",
//                        text = stringResource(R.string.current_weather_moscow),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "",
//                        text = stringResource(
//                            R.string.temperature_format,
//                            currentState.data.temperature,
//                        ),
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
                            text = "",
//                            text = stringResource(R.string.forecast_button),
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
