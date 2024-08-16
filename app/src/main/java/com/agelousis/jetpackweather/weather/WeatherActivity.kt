package com.agelousis.jetpackweather.weather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agelousis.jetpackweather.ui.theme.JetpackWeatherTheme
import com.agelousis.jetpackweather.utils.extensions.weatherDrawerNavigationType
import com.agelousis.jetpackweather.weather.ui.WeatherActivityBottomNavigationView

class WeatherActivity: AppCompatActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            //window?.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
            JetpackWeatherTheme {
                // A surface container using the 'background' color from the theme
                val windowSize = calculateWindowSizeClass(
                    activity = this
                )
                WeatherActivityBottomNavigationView(
                    viewModel = viewModel(),
                    weatherDrawerNavigationType = windowSize.weatherDrawerNavigationType
                )
            }
        }
    }
}