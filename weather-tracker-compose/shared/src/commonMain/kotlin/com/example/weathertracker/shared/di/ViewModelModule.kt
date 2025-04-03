package com.example.weathertracker.shared.di

import com.example.weathertracker.shared.presentation.forecast.ForecastViewModel
import com.example.weathertracker.shared.presentation.weather.CurrentWeatherViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


internal val viewModelModule: Module = module {

    viewModelOf(::CurrentWeatherViewModel)
    viewModelOf(::ForecastViewModel)
}