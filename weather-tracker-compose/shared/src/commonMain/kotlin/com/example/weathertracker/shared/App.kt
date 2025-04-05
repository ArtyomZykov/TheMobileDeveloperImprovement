package com.example.weathertracker.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.weathertracker.shared.presentation.weather.CurrentWeatherScreen
import com.example.weathertracker.shared.presentation.weather.CurrentWeatherViewModel
import io.ktor.websocket.Frame.Text
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {

//    val viewModel: CurrentWeatherViewModel = koinViewModel()
    CurrentWeatherScreen(
        onForecastClick = {

        }
    )
//    Text("asdfasasdfadf" + viewModel.state.collectAsState().value.toString())
}
