package com.agelousis.jetpackweather.weather.extensions

import android.content.Intent
import com.agelousis.jetpackweather.weather.WeatherActivity

fun WeatherActivity.restart() {
    startActivity(
        Intent(
            this,
            WeatherActivity::class.java
        )
    )
    finish()
}