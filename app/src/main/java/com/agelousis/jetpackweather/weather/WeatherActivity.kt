package com.agelousis.jetpackweather.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.agelousis.jetpackweather.ui.theme.JetpackWeatherTheme
import com.agelousis.jetpackweather.weather.ui.WeatherActivityBottomNavigationLayout

class WeatherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JetpackWeatherTheme {
                window?.navigationBarColor = MaterialTheme.colorScheme.secondaryContainer.toArgb()
                // A surface container using the 'background' color from the theme
                WeatherActivityBottomNavigationLayout()
            }
        }
    }
}