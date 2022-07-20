package com.agelousis.jetpackweather.weather.enumerations

import com.agelousis.jetpackweather.R

enum class TemperatureUnitType {
    CELSIUS,
    FAHRENHEIT;


    val icon
        get() = when(this) {
            CELSIUS -> R.drawable.ic_celsius
            FAHRENHEIT -> R.drawable.ic_fahrenheit
        }
}