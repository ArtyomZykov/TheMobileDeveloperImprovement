package com.example.weathertracker.shared.util

import kotlin.math.pow
import kotlin.math.roundToInt

object Temperature {

    fun Double.kelvinToCelsius(): Double {
        return (this - 273.15).roundTo(1)
    }

    fun Double.kelvinToFahrenheit(): Double {
        return ((this - 273.15) * 9 / 5 + 32).roundTo(1)
    }

    private fun Double.roundTo(decimalPlaces: Int): Double {
        val factor = 10.0.pow(decimalPlaces)
        return (this * factor).roundToInt() / factor
    }
}
