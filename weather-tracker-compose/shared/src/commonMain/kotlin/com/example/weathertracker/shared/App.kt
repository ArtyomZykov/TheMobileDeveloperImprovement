package com.example.weathertracker.shared

import androidx.compose.runtime.Composable
import com.example.weathertracker.shared.presentation.forecast.ForecastScreen
import com.example.weathertracker.shared.presentation.weather.CurrentWeatherScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.KoinContext

@Composable
fun App() {

    val navigator = rememberNavigator()

    PreComposeApp {
        KoinContext {
            NavHost(
                navigator = navigator,
                initialRoute = "/current_weather",
            ) {
                scene(route = "/current_weather") {
                    CurrentWeatherScreen(
                        onForecastClick = {
                            navigator.navigate("/forecast")
                        }
                    )
                }
                scene(route = "/forecast") {
                    ForecastScreen(
                        onBackClick = {
                            navigator.goBack()
                        }
                    )
                }
            }
        }
    }
}
