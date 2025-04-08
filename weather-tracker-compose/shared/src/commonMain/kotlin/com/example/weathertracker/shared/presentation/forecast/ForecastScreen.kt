package com.example.weathertracker.shared.presentation.forecast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.weathertracker.shared.resources.Res
import com.example.weathertracker.shared.resources.forecast_title
import com.example.weathertracker.shared.resources.temperature_range_format
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

object ForecastScreen : Screen {

    override val key: String = "/forecast"

    @Composable
    fun Content(onBackClick: () -> Unit) {
        ForecastScreen(onBackClick = onBackClick)
    }
}

@Composable
private fun ForecastScreen(
    onBackClick: () -> Unit,
    viewModel: ForecastViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.forecast_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
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
        when (val state = uiState) {
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    val forecasts = state.data.forecasts
                    items(
                        count = forecasts.size,
                        key = { index ->
                            forecasts[index].date
                        },
                    ) { index ->
                        ForecastItem(forecast = forecasts[index])
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
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun ForecastItem(forecast: ForecastState.ForecastItemState) {
    val basePaddingModifier = Modifier.padding(horizontal = 16.dp)
    Column {
        Text(
            modifier = basePaddingModifier.padding(top = 16.dp),
            text = forecast.date,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = basePaddingModifier.height(4.dp))
        Text(
            modifier = basePaddingModifier,
            text = stringResource(
                Res.string.temperature_range_format,
                forecast.maxTemp,
                forecast.minTemp,
            ),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = basePaddingModifier.height(4.dp))
        Text(
            modifier = basePaddingModifier.padding(bottom = 16.dp),
            text = forecast.description,
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider(basePaddingModifier)
    }
}