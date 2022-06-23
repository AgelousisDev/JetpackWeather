package com.agelousis.jetpackweather.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.agelousis.jetpackweather.ui.theme.JetpackWeatherTheme
import com.agelousis.jetpackweather.weather.ui.WeatherActivityBottomNavigationLayout

class WeatherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window?.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
            JetpackWeatherTheme {
                // A surface container using the 'background' color from the theme
                WeatherActivityBottomNavigationLayout()
            }
        }
    }
}