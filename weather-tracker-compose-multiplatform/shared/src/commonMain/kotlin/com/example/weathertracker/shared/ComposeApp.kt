package com.example.weathertracker.shared

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.weathertracker.shared.presentation.forecast.ForecastScreen
import com.example.weathertracker.shared.presentation.settings.SettingsScreen
import com.example.weathertracker.shared.presentation.weather.CurrentWeatherScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun ComposeApp() {

    val navigator = rememberNavigator()

    PreComposeApp {
        MaterialTheme {
            NavHost(
                navigator = navigator,
                initialRoute = CurrentWeatherScreen.key,
            ) {
                CurrentWeatherScreen.apply {
                    scene(route = key) {
                        Content(
                            onForecastClick = {
                                navigator.navigate(ForecastScreen.key)
                            },
                            onSettingsClick = {
                                navigator.navigate(SettingsScreen.key)
                            }
                        )
                    }
                }
                ForecastScreen.apply {
                    scene(route = key) {
                        Content(
                            onBackClick = {
                                navigator.goBack()
                            }
                        )
                    }
                }
                SettingsScreen.apply {
                    scene(route = key) {
                        Content(
                            onBackClick = {
                                navigator.goBack()
                            }
                        )
                    }
                }
            }
        }
    }
}
