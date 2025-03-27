package com.example.weathertracker.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weathertracker.presentation.weather.CurrentWeatherScreen
import com.example.weathertracker.presentation.forecast.ForecastScreen

@Composable
fun WeatherTrackerApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "weather"
    ) {
        composable("weather") {
            CurrentWeatherScreen(
                onForecastClick = {
                    navController.navigate("forecast")
                },
            )
        }
        composable("settings") {
            ForecastScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
